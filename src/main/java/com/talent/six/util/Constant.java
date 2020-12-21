package com.talent.six.util;

public class Constant {

    //--------------token--------------

    //jwt_token密钥
    public static final String JWT_SECRET = "437bba8e0bf58337674f4539e75186ac";

    //jwt_token过期时间
    public static final long EXPIRE_TIME = 168 * 60 * 60 * 1000;    //毫秒

    //jwt_token过期时间，jwt硬性过期时间
    public static final long JWT_EXPIRE_TIME = 170 * 60 * 60 * 1000;    //毫秒

    //--------------消息队列--------------
}
