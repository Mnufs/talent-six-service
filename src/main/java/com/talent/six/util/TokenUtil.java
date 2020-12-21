package com.talent.six.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class TokenUtil {


    /**
     * jwt解析token，转换json格式
     *
     * @param token
     * @return
     */
    public static JSONObject getInfo(String token) {

        try {
            if (StringUtils.isEmpty(token) || "null".equals(token)){
                return new JSONObject();
            }

            return JSONObject.parseObject(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            log.error("解析token错误", e);
        }
        return new JSONObject();
    }
}
