package com.talent.six.controller;

import com.talent.six.exception.TokenException;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Token异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = TokenException.class)
    @ResponseBody
    public BaseResponse TokenErrorHandler(TokenException e) {
        return BaseResponseBuilder.buildBaseResponse(e.getCode());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse exceptionErrorHandler(Exception e) {
        log.error("未知异常 -->", e);
        return BaseResponseBuilder.buildBaseResponse(ReturnCode.RESULT_FAIL);
    }
}
