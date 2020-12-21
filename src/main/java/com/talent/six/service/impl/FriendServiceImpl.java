package com.talent.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.mapper.FriendMapper;
import com.talent.six.model.Friend;
import com.talent.six.model.FriendRequestRecord;
import com.talent.six.model.FriendRequestRecordVO;
import com.talent.six.model.User;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import com.talent.six.other.ws.WebSocketServer;
import com.talent.six.service.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private FriendMapper friendMapper;


    /**
     * 查询好友列表
     *
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse listFriend(String userId) throws Exception {

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, friendMapper.listFriend(userId));
    }

    /**
     * 查询好友申请记录
     *
     * @param userId    用户ID
     *
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse listFriendRequestRecord(String userId) throws Exception {

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, friendMapper.listFriendRequestRecord(userId));
    }

    /**
     * 发送好友申请
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse sendFriendRequest(FriendRequestRecord friendRequestRecord) throws Exception {

        if(friendMapper.saveFriendRequestRecord(friendRequestRecord) == 0){
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null,"发送失败，请稍后再试！");
        }

        User user = new User();
        user.setId(Long.parseLong(friendRequestRecord.getTargetId()));

        FriendRequestRecordVO frr = friendMapper.getFriendRequestRecordByID(friendRequestRecord.getId());

        JSONObject responseJson = new JSONObject(7);
        responseJson.put("id", frr.getId());
        responseJson.put("userId", frr.getUserId());
        responseJson.put("nickName", frr.getNickName());
        responseJson.put("avatar", frr.getAvatar());
        responseJson.put("message", frr.getMessage());
        responseJson.put("status", frr.getStatus());
        responseJson.put("dataType", "friendRequest");

        //发送给个人
        webSocketServer.sendSingleMessage(friendRequestRecord.getTargetId(), responseJson.toJSONString());

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, null,"发送成功！");
    }

    /**
     * 获取未读的好友申请数量
     *
     * @param userId    用户ID
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse getUnreadFriendRequestCount(String userId) throws Exception {

        int count = friendMapper.getUnreadFriendRequestCount(userId);

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, count);
    }

    /**
     * 通过好友验证
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse passFriendValidation(FriendRequestRecord friendRequestRecord) throws Exception {

        int result = friendMapper.updateFriendRequestStatus(friendRequestRecord);

        if(result <= 0){

            log.error("[好友服务] 修改好友申请记录信息失败，friendRequestRecordID：{}", friendRequestRecord.getId());
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR);
        }

        //查询好友申请信息
        FriendRequestRecordVO friendRequestRecordVO = friendMapper.getFriendRequestRecordByID(friendRequestRecord.getId());


        List<Friend> friendList = new ArrayList<>(2);
        Friend friend = new Friend();
        friend.setUserId(friendRequestRecordVO.getUserId());
        friend.setFriendId(friendRequestRecordVO.getTargetId());
        friend.setStatus("1");
        friend.setCreateTime(LocalDateTime.now());
        friend.setModifyTime(LocalDateTime.now());

        friendList.add(friend);

        friend = new Friend();
        friend.setUserId(friendRequestRecordVO.getTargetId());
        friend.setFriendId(friendRequestRecordVO.getUserId());
        friend.setStatus("1");
        friend.setCreateTime(LocalDateTime.now());
        friend.setModifyTime(LocalDateTime.now());

        friendList.add(friend);

        //批量保存好友信息
        if(friendMapper.batchSaveFriend(friendList) == 0){

            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            log.error("[好友服务] 批量保存好友信息失败，friendRequestRecordID：{}", friendRequestRecord.getId());
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR);
        }

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS);
    }

    /**
     * 忽略好友申请
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse ignoreFriendRequest(FriendRequestRecord friendRequestRecord) throws Exception {

        int result = friendMapper.updateFriendRequestStatus(friendRequestRecord);

        if(result <= 0){
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR);
        }

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS);
    }
}
