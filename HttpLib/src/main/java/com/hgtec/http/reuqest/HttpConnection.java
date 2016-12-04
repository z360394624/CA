package com.hgtec.http.reuqest;

import android.util.Log;
import com.hgtec.http.HttpConstants;
import com.hgtec.http.bean.HttpResult;

import java.io.*;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import static com.hgtec.http.HttpConstants.Encoding.UTF_8;

/**
 * Created by z3603 on 2016/12/3.
 */
public class HttpConnection {

    private static final String TAG = "HttpConnection";

    private HttpURLConnection mConnection;

    public HttpConnection() {
    }

    public HttpResult doPost(String requestUrl,
                             String params,
                             int connectionTimeout,
                             int readTimeout,
                             Map<String, String> header,
                             boolean isGZIP) {
        HttpResult result = null;
        URL url = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        OutputStream os = null;
        // Http result data
        String data = null;
        // server data last modified time
        String lastModified = null;
        int responseCode;
        try {
            url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpConstants.Method.POST);
            // assemble header data
            assembleHeader(connection, header, isGZIP);
            // connect timeout millis
            connection.setConnectTimeout(connectionTimeout);
            // read timeout millis
            connection.setReadTimeout(readTimeout);
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // connect
            connection.connect();
            // get outputstream for post params
            os = connection.getOutputStream();
            // send params
            os.write(params.getBytes(HttpConstants.Encoding.UTF_8));
            // responsecode
            responseCode = connection.getResponseCode();
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                    is = connection.getInputStream();
                    if (isGZIP) {
                        is = new GZIPInputStream(is);
                    }
                    data = readStream(is);
                    lastModified = connection.getHeaderField(HttpConstants.HeaderField.LAST_MODIFIED);
                    break;
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    //redirect url
                    String redirectUrl = connection.getHeaderField(HttpConstants.HeaderField.LOCATION);
                    this.closeStream(is, os, connection);
                    this.doPost(redirectUrl, params, connectionTimeout, readTimeout, header, isGZIP);
                    break;
                case HttpURLConnection.HTTP_NOT_MODIFIED:
                    break;
                default:
                    break;
            }
            return new HttpResult(responseCode, lastModified, data);
        } catch (ProtocolException pe) {
            pe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeStream(is, os, connection);
        }
        return result;
    }

    public HttpResult doGet(String requestUrl,
                            String params,
                            int connectionTimeout,
                            int readTimeout,
                            Map<String, String> header,
                            boolean isGZIP) {
        HttpResult result = null;
        URL url = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        OutputStream os = null;
        // Http result data
        String data = null;
        // server data last modified time
        String lastModified = null;
        int responseCode;
        try {
            requestUrl = params != null ? requestUrl + params : requestUrl;
            url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpConstants.Method.GET);
            // assemble header data
            assembleHeader(connection, header, isGZIP);
            // connect timeout millis
            connection.setConnectTimeout(connectionTimeout);
            // read timeout millis
            connection.setReadTimeout(readTimeout);
            connection.connect();
            responseCode = connection.getResponseCode();
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                    is = connection.getInputStream();
                    if (isGZIP) {
                        is = new GZIPInputStream(is);
                    }
                    data = readStream(is);
                    lastModified = connection.getHeaderField(HttpConstants.HeaderField.LAST_MODIFIED);
                    break;
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    //redirect url
                    String redirectUrl = connection.getHeaderField(HttpConstants.HeaderField.LOCATION);
                    this.closeStream(is, os, connection);
                    this.doGet(redirectUrl, params, connectionTimeout, readTimeout, header, isGZIP);
                    break;
                case HttpURLConnection.HTTP_NOT_MODIFIED:
                    break;
                default:
                    break;
            }
            return new HttpResult(responseCode, lastModified, data);
        } catch (ProtocolException pe) {
            pe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeStream(is, os, connection);
        }
        return result;
    }


    private void assembleHeader(HttpURLConnection connection, Map<String, String> header, boolean isGZIP) {
        if (connection == null) {
            Log.e(TAG, "HttpURLConnection is null");
            return;
        }
        if (header == null) {
            Log.e(TAG, "Header is null");
            return;
        }
        if (isGZIP) {
            connection.setRequestProperty(HttpConstants.HeaderField.ACCEPT_ENCODING, HttpConstants.Encoding.GZIP);
        }
        for (String key : header.keySet()) {
            if (key == null) continue;
            String value = header.get(key);
            Log.i(TAG, "HEADER: " + key + "->" + value);
            connection.setRequestProperty(key, value);
        }
    }

    private String readStream(InputStream is) throws IOException {
        if (is == null) {
            Log.e(TAG, "response error, InputStream is null");
            return null;
        }
        InputStreamReader isr = new InputStreamReader(is);
        char[] buf = new char[256];
        StringBuffer sb = new StringBuffer();
        int len;
        while ((len = isr.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void closeStream(InputStream is, OutputStream os, HttpURLConnection connection) {
        try {
            if (is != null) {
                is.close();
                is = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            is = null;
            os = null;
        }
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

}
