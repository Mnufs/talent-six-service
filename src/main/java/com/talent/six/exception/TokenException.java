package com.talent.six.exception;

import com.talent.six.other.enums.ReturnCode;

/**
 * token异常类
 */
public class TokenException extends RuntimeException {

    private ReturnCode code;

    public TokenException(ReturnCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public ReturnCode getCode() {
        return code;
    }

    public void setCode(ReturnCode code) {
        this.code = code;
    }

}
