package com.talent.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.mapper.UserMapper;
import com.talent.six.model.User;
import com.talent.six.model.UserVO;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import com.talent.six.service.UserService;
import com.talent.six.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.Clock;

import static com.talent.six.util.Constant.EXPIRE_TIME;
import static com.talent.six.util.Constant.JWT_EXPIRE_TIME;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询是否存在
     *
     * @param userName
     * @return
     * @throws Exception
     */
    @Override
    public int isExistByUserName(String userName) throws Exception {

        return userMapper.isExistByUserName(userName);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse register(User user) throws Exception {

        try {

            //新增用户信息
            if (userMapper.save(user) == 0) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "注册失败，请稍后再试！");
            }
        } catch (DuplicateKeyException e) {

            //唯一性校验提示
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "用户已被注册！");
        }

        //生成token
        String token = JwtUtil.createJWT("{userId:" + user.getId() + "}", JWT_EXPIRE_TIME);
        long tokenExpireTime = Clock.systemDefaultZone().millis() + EXPIRE_TIME;

        JSONObject resultJson = new JSONObject(5);
        resultJson.put("token", token);
        resultJson.put("tokenExpireTime", tokenExpireTime);
        resultJson.put("userId", user.getId());
        resultJson.put("userName", user.getUserName());
        resultJson.put("nickName", user.getNickName());

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, resultJson, "注册成功！");
    }

    /**
     * 登录
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse login(User user) throws Exception {

        UserVO userInfo = userMapper.get(user);

        if (userInfo == null) {
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "用户名或密码不正确！");
        }

        //生成token
        String token = JwtUtil.createJWT("{userId:" + userInfo.getId() + "}", JWT_EXPIRE_TIME);
        long tokenExpireTime = Clock.systemDefaultZone().millis() + EXPIRE_TIME;

        JSONObject resultJson = new JSONObject(5);
        resultJson.put("token", token);
        resultJson.put("tokenExpireTime", tokenExpireTime);
        resultJson.put("userId", userInfo.getId());
        resultJson.put("userName", userInfo.getUserName());
        resultJson.put("nickName", userInfo.getNickName());

        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, resultJson, "登录成功！");

    }

    /**
     * 查询用户详细信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public BaseResponse get(User user) throws Exception {
        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, userMapper.get(user));
    }
}
