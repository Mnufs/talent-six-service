package com.talent.six.service;

import com.talent.six.model.User;
import com.talent.six.other.response.BaseResponse;

public interface UserService {

    /**
     * 根据用户名查询是否存在
     *
     * @param userName
     * @return
     * @throws Exception
     */
    int isExistByUserName(String userName) throws Exception;

    /**
     * 注册
     *
     * @param user
     * @return
     * @throws Exception
     */
    BaseResponse register(User user) throws Exception;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    BaseResponse login(User user) throws Exception;

    /**
     * 查询用户详细信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    BaseResponse get(User user) throws Exception;
}
