package com.talent.six.other.ws;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.filter.AllowedOriginConfig;
import com.talent.six.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 开启webSocket支持
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private AllowedOriginConfig allowedOriginConfig;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //从配置文件获取允许跨域的网址
        String[] allowedOrigins = allowedOriginConfig.getAllowedOrigins();

        registry.addEndpoint("/ws")
                .addInterceptors(new HandshakeInterceptor() {
                    /**
                     * websocket握手
                     */
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
                        //获取token认证
                        String token = req.getServletRequest().getParameter("token");
                        //解析token获取用户信息
                        Principal user = checkToken(token);
                        if (user == null) {   //如果token认证失败user为null，返回false拒绝握手
                            return false;
                        }
                        //保存认证用户
                        attributes.put("user", user);
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

                    }
                })
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        //设置认证用户
                        return (Principal) attributes.get("user");
                    }
                })
                .setAllowedOrigins(allowedOrigins)            //设置允许跨域的网址，可能会有多个
                .withSockJS();                                //使用sockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //group群聊，user私聊
        registry.enableSimpleBroker("/group", "/user");
    }

    /**
     * 消息传输参数配置
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(8192) //设置消息字节数大小
                .setSendBufferSizeLimit(8192)//设置消息缓存大小
                .setSendTimeLimit(10000); //设置消息发送时间限制毫秒
    }

    private Principal checkToken(String token) throws Exception {
        Principal user = null;

        try {
            if (StringUtils.isNotEmpty(token) && !"null".equals(token)) {
                //校验token
                Claims claims = JwtUtil.parseJWT(token);
                if (null != claims) {
                    Long userId = JSONObject.parseObject(claims.getSubject()).getLong("userId");
                    if (userId == null || userId == 0) {
                        //用户ID不正确
                        return user;
                    } else {
                        user = () -> String.valueOf(userId);
                        return user;
                    }
                } else {
                    //token为空或不正确
                    return user;
                }
            } else {
                //token为空或不正确
                return user;
            }
        } catch (Exception e) {
            return user;
        }
    }
}
