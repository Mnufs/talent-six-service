package com.talent.six.filter;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.exception.TokenException;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.Clock;

/**
 * token校验
 */
@Slf4j
public class TokenFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        ReturnCode returnCode = null;

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //判断接口是否需要Token校验
        TokenRequired methodAnnotation = method.getAnnotation(TokenRequired.class);

        //有 @TokenRequired 注解，需要校验
        if (methodAnnotation != null) {
            String token = request.getHeader("token");
            String temp = request.getHeader("tokenExpireTime");
            long tokenExpireTime = Long.parseLong(StringUtils.isEmpty(temp) || "null".equals(temp) ? "0" : temp);

            if (StringUtils.isNotEmpty(token) && !"null".equals(token)) {
                //校验token
                try {
                    Claims claims = JwtUtil.parseJWT(token);
                    if (null != claims) {
                        //校验token过期时间
                        if (Clock.systemDefaultZone().millis() > tokenExpireTime) {
                            returnCode = ReturnCode.TOKEN_EXPIRE;
                        } else {
                            Long userId = JSONObject.parseObject(claims.getSubject()).getLong("userId");
                            if (userId == null || userId == 0)
                                //用户ID不正确
                                returnCode = ReturnCode.TOKEN_USER_ERROR;
                        }
                    } else {
                        //token为空或不正确
                        returnCode = ReturnCode.TOKEN_ERROR;
                    }
                } catch (ExpiredJwtException e) {
                    //token过期
                    returnCode = ReturnCode.TOKEN_EXPIRE;
                }
            } else {
                //token为空或不正确
                returnCode = ReturnCode.TOKEN_ERROR;
            }

            if (returnCode != null) {
                throw new TokenException(returnCode, returnCode.getMsg());
            } else {
                return true;
            }
        }
        return true;
    }

}