package com.hgtec.http.bean;

import java.util.Map;

/**
 * Created by Lucius on 2016/12/4.
 */
public class HttpParams {

    private Map<String, String> params;

    private Map<String, String> header;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
