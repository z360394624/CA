package com.hgtec.http.reuqest;

import android.os.AsyncTask;
import com.hgtec.http.bean.HttpParams;
import com.hgtec.http.bean.HttpResult;

import java.util.Map;

/**
 * Created by Lucius on 2016/12/4.
 */
public class HttpRequest {

    private static final String TAG = "HttpRequest";

    private int DEFAULT_CONNECT_TIMEOUT = 10000;

    private int DEFAULT_READ_TIMEOUT = 10000;


    private class RequestAsyncTask extends AsyncTask<Object, Integer, HttpResult> {

        @Override
        protected HttpResult doInBackground(Object... params) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }


    public int getConnectTimeout() {
        return DEFAULT_CONNECT_TIMEOUT;
    }

    public int getReadTimeout() {
        return DEFAULT_READ_TIMEOUT;
    }
}
