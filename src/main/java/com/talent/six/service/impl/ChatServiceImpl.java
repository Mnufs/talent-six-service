package com.talent.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.mapper.FriendMapper;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import com.talent.six.other.ws.WebSocketServer;
import com.talent.six.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private FriendMapper friendMapper;


    /**
     * 私聊
     *
     * @param receiver       接收人
     * @param sender         发送人
     * @param content        发送内容
     * @param senderNickName 发送人昵称
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse single(String receiver, String sender, String content, String senderNickName) throws Exception {

        //判断接收人是否是发送人的好友
        if (friendMapper.isFriend(sender, receiver) == 0) {
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.NOT_FRIEND);
        }

        long time = Clock.systemDefaultZone().millis();

        JSONObject responseJson = new JSONObject(5);
        responseJson.put("sender", sender);
        responseJson.put("content", content);
        responseJson.put("time", time);
        responseJson.put("senderNickName", senderNickName);
        responseJson.put("dataType", "chat");

        //发送给个人
        webSocketServer.sendSingleMessage(receiver, responseJson.toJSONString());

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, time, "发送成功");
    }
}
