package com.talent.six.other.response;


import com.talent.six.other.enums.ReturnCode;

public class BaseResponseBuilder {

    /**
     * 返回响应码、消息、对象
     *
     * @param returnCode
     * @param message
     * @param t
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> buildBaseResponse(ReturnCode returnCode, T t, String message) {
        BaseResponse<T> result = new BaseResponse<T>();
        result.setCode(returnCode.getCode());
        result.setMessage(message);
        result.setData(t);
        return result;
    }

    /**
     * 返回响应码、对象
     *
     * @param returnCode
     * @param t
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> buildBaseResponse(ReturnCode returnCode, T t) {
        BaseResponse<T> result = new BaseResponse<>();
        result.setCode(returnCode.getCode());
        result.setMessage(returnCode.getMsg());
        result.setData(t);
        return result;
    }

    /**
     * 返回响应码
     *
     * @param returnCode
     * @return
     */
    public static BaseResponse<String> buildBaseResponse(ReturnCode returnCode) {
        BaseResponse<String> result = new BaseResponse<>();
        result.setCode(returnCode.getCode());
        result.setMessage(returnCode.getMsg());
        return result;
    }

}
