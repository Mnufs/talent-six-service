package com.talent.six.other.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServer {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendGroupMessage(String content) throws Exception {
        template.convertAndSend("/group", content);
    }

    public void sendSingleMessage(String userId, String content) throws Exception {
        template.convertAndSendToUser(userId, "/single", content);
    }
}
