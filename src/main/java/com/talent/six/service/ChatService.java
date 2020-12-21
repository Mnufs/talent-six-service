package com.talent.six.service;

import com.talent.six.other.response.BaseResponse;

public interface ChatService {

    /**
     * 私聊
     *
     * @param receiver          接收人
     * @param sender            发送人
     * @param content           发送内容
     * @param senderNickName    发送人昵称
     * @return
     * @throws Exception
     */
    BaseResponse single(String receiver, String sender, String content, String senderNickName) throws Exception;
}
