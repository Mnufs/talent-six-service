package com.talent.six.service;

import com.talent.six.model.FriendRequestRecord;
import com.talent.six.other.response.BaseResponse;

public interface FriendService {


    /**
     * 查询好友列表
     *
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    BaseResponse listFriend(String userId) throws Exception;


    /**
     * 查询好友申请记录
     *
     * @param userId    用户ID
     *
     * @return
     * @throws Exception
     */
    BaseResponse listFriendRequestRecord(String userId) throws Exception;

    /**
     * 发送好友申请
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    BaseResponse sendFriendRequest(FriendRequestRecord friendRequestRecord) throws Exception;

    /**
     * 获取未读的好友申请数量
     *
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    BaseResponse getUnreadFriendRequestCount(String userId) throws Exception;

    /**
     * 通过好友验证
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    BaseResponse passFriendValidation(FriendRequestRecord friendRequestRecord) throws Exception;

    /**
     * 忽略好友申请
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    BaseResponse ignoreFriendRequest(FriendRequestRecord friendRequestRecord) throws Exception;
}
