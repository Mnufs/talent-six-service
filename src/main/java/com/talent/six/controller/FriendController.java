package com.talent.six.controller;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.filter.TokenRequired;
import com.talent.six.model.FriendRequestRecord;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import com.talent.six.service.FriendService;
import com.talent.six.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/friend")
@Slf4j
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 查询好友列表
     *
     * @return
     */
    @PostMapping("/listFriend")
    @ResponseBody
    @TokenRequired
    public BaseResponse listFriend(@RequestHeader("token") String token) {

        try {

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            return friendService.listFriend(userId.toString());

        } catch (Exception e) {

            log.error("[好友服务] 查询好友列表接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 查询好友申请记录
     *
     * @return
     */
    @PostMapping("/listFriendRequestRecord")
    @ResponseBody
    @TokenRequired
    public BaseResponse listFriendRequestRecord(@RequestHeader("token") String token) {

        try {

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            return friendService.listFriendRequestRecord(userId.toString());

        } catch (Exception e) {

            log.error("[好友服务] 查询好友申请记录接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 发送好友申请
     *
     * @return
     */
    @PostMapping("/sendFriendRequest")
    @ResponseBody
    @TokenRequired
    public BaseResponse sendFriendRequest(@RequestHeader("token") String token,
                                          @RequestBody String json) {

        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            String targetId = param.getString("targetId");

            String msg = param.getString("message");

            //非空校验
            if (StringUtils.isEmpty(targetId)) {
                message = "目标ID不能为空！";
            }

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            FriendRequestRecord friendRequestRecord = new FriendRequestRecord.Builder()
                    .message(msg)
                    .userId(userId.toString())
                    .targetId(targetId)
                    .status("0")
                    .createTime(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();


            return friendService.sendFriendRequest(friendRequestRecord);

        } catch (Exception e) {

            log.error("[好友服务] 发送好友申请接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }


    /**
     * 获取未读的好友申请数量
     *
     * @return
     */
    @GetMapping("/getUnreadFriendRequestCount")
    @ResponseBody
    @TokenRequired
    public BaseResponse getUnreadFriendRequestCount(@RequestHeader("token") String token) {

        try {

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            return friendService.getUnreadFriendRequestCount(userId.toString());

        } catch (Exception e) {

            log.error("[好友服务] 获取未读的好友申请数量接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 通过好友验证
     *
     * @return
     */
    @PostMapping("/passFriendValidation")
    @ResponseBody
    @TokenRequired
    public BaseResponse passFriendValidation(@RequestHeader("token") String token,
                                             @RequestBody String json) {

        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            String friendRequestId = param.getString("friendRequestId");

            //非空校验
            if (StringUtils.isEmpty(friendRequestId)) {
                message = "好友申请ID不能为空！";
            }

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            FriendRequestRecord friendRequestRecord = new FriendRequestRecord.Builder()
                    .id(Long.parseLong(friendRequestId))
                    .targetId(userId.toString())
                    .status("1")
                    .modifyTime(LocalDateTime.now())
                    .build();

            return friendService.passFriendValidation(friendRequestRecord);

        } catch (Exception e) {

            log.error("[好友服务] 通过好友验证接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 忽略好友申请
     *
     * @return
     */
    @PostMapping("/ignoreFriendRequest")
    @ResponseBody
    @TokenRequired
    public BaseResponse ignoreFriendRequest(@RequestHeader("token") String token,
                                             @RequestBody String json) {

        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            String friendRequestId = param.getString("friendRequestId");

            //非空校验
            if (StringUtils.isEmpty(friendRequestId)) {
                message = "好友申请ID不能为空！";
            }

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }

            //获取token中userId
            Long userId = TokenUtil.getInfo(token).getLong("userId");

            FriendRequestRecord friendRequestRecord = new FriendRequestRecord.Builder()
                    .id(Long.parseLong(friendRequestId))
                    .targetId(userId.toString())
                    .status("2")
                    .modifyTime(LocalDateTime.now())
                    .build();

            return friendService.ignoreFriendRequest(friendRequestRecord);

        } catch (Exception e) {

            log.error("[好友服务] 忽略好友申请接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

}

