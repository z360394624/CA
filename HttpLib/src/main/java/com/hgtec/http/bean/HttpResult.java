package com.hgtec.http.bean;

/**
 * Created by z3603 on 2016/12/3.
 */
public class HttpResult {

    public HttpResult(int responseCode, String lastModified, String responseData) {
        this.mResponseCode = responseCode;
        this.mLastModified = lastModified;
        this.mResponseData = responseData;
    }

    /**
     * 200/301/302/304/500
     */
    public int mResponseCode;
    /**
     * if mResponseCode == 304, get HeaderField "Last-Modified"
     */
    public String mLastModified;
    /**
     * request data
     */
    public String mResponseData;


}
