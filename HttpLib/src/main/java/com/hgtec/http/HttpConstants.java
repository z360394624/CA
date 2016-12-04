package com.hgtec.http;

/**
 * Created by z3603 on 2016/12/4.
 */
public class HttpConstants {

    public static final class Method {
        public static final String POST = "POST";
        public static final String GET = "GET";
    }

    public static final class Encoding {
        public static final String UTF_8 = "UTF-8";
        public static final String GZIP = "gzip";
    }

    public static final class HeaderField {
        public static final String ACCEPT_ENCODING = "Accept-Encoding";
        public static final String LOCATION = "Location";
        public static final String LAST_MODIFIED = "Last-Modified";
    }

    public static final class Path {
        public static final String CACHE_PATH = "/cache/";
    }




}
