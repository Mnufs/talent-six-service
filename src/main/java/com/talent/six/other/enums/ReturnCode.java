package com.talent.six.other.enums;

public enum ReturnCode {

    SUCCESS("0", "操作成功！"),
    ERROR("1", "操作失败！"),
    TOKEN_ERROR("2", "Token为空或不正确！"),
    TOKEN_EXPIRE("3", "Token过期！"),
    TOKEN_USER_ERROR("4", "userId不正确！"),
    NOT_FRIEND("5", "您还不是对方的好友，请查看是否通过朋友验证！"),
    RESULT_FAIL("500", "业务处理失败！");

    private String code;
    private String msg;

    ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
