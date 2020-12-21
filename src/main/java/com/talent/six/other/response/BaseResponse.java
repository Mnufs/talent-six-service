package com.talent.six.other.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private String code;        //业务响应码（0：成功，1：失败）

    private String message;     //响应消息

    T data;                     //参数对象
}
