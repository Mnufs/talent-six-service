package com.talent.six.controller;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.filter.TokenRequired;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import com.talent.six.service.ChatService;
import com.talent.six.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;


    /**
     * 私聊
     *
     * @return
     */
    @PostMapping("/single")
    @ResponseBody
    @TokenRequired
    public BaseResponse single(@RequestHeader("token") String token,
                               @RequestBody String json) {
        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            //接收人
            String receiver = param.getString("receiver");

            //内容
            String content = param.getString("content");

            //发送人昵称
            String senderNickName = param.getString("senderNickName");

            //非空校验
            if (StringUtils.isEmpty(receiver)) {
                message = "接收人不能为空！";
            } else if (StringUtils.isEmpty(content)) {
                message = "发送内容不能为空！";
            } else if (StringUtils.isEmpty(senderNickName)) {
                message = "发送人昵称不能为空！";
            }

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }

            return chatService.single(receiver, userId.toString(), content, senderNickName);

        } catch (Exception e) {

            log.error("[聊天服务] 私聊接口异常",e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR,null,"请求失败，请稍后再试！");
        }
    }


}

