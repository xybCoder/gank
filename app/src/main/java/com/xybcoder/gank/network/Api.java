package com.xybcoder.gank.network;

public class Api {
    public static final String GANK_HOST = "http://gank.io/api/";
    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        switch (hostType) {
            case HostType.NEWS_TYPE:
                return "";
            case HostType.GANK_TYPE:
                return Api.GANK_HOST;
        }
        return "";
    }
}
