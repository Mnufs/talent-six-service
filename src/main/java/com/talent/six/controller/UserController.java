package com.talent.six.controller;

import com.alibaba.fastjson.JSONObject;
import com.talent.six.filter.TokenRequired;
import com.talent.six.model.User;
import com.talent.six.other.enums.ReturnCode;
import com.talent.six.other.response.BaseResponse;
import com.talent.six.other.response.BaseResponseBuilder;
import com.talent.six.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public BaseResponse login(@RequestBody String json) {

        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            String userName = param.getString("userName");

            String password = param.getString("password");

            //非空校验
            if (StringUtils.isEmpty(userName)) {
                message = "用户名不能为空！";
            } else if (StringUtils.isEmpty(password)) {
                message = "密码不能为空！";
            }

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }

            //密码进行md5加密
            password = DigestUtils.md5DigestAsHex(password.getBytes());

            User user = new User.Builder()
                    .userName(userName)
                    .password(password)
                    .build();

            return userService.login(user);

        } catch (Exception e) {

            log.error("[用户服务] 登录接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 注册
     *
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public BaseResponse register(@RequestBody String json) {

        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            String userName = param.getString("userName");

            String password = param.getString("password");

            String nickName = param.getString("nickName");

            String passwordConfirm = param.getString("passwordConfirm");

            //非空校验
            if (StringUtils.isEmpty(userName)) {

                message = "用户名不能为空！";
            } else if (StringUtils.isEmpty(password)) {

                message = "密码不能为空！";
            } else if (StringUtils.isEmpty(nickName)) {

                message = "昵称不能为空！";
            } else if (!userName.matches("^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$")) {

                message = "用户名必须是以字母开头的6-20个字母、数字、下划线和减号，且不支持中文！";
            } else if (!nickName.matches("^[\\w\\u4e00-\\u9fa5]{1,10}$")) {

                message = "昵称必须1-10个字符，只能包含数字英文中文下划线，不能包含空格和特殊符号！";
            } else if (userService.isExistByUserName(userName) > 0) {

                message = "用户名已经被使用啦！";
            } else if (!password.matches("^(\\w){8,15}$")) {

                message = "密码长度最少8位，最多15位！";
            } else if (!password.equals(passwordConfirm)) {

                message = "两次填写的密码不一致！";
            }

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }

            //密码进行md5加密
            password = DigestUtils.md5DigestAsHex(password.getBytes());

            User user = new User.Builder()
                    .userName(userName)
                    .password(password)
                    .nickName(nickName)
                    .status("1")
                    .createTime(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();

            return userService.register(user);

        } catch (Exception e) {

            log.error("[用户服务] 注册用户信息接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 校验用户名是否重复
     *
     * @return
     */
    @PostMapping("/isExistByUserName")
    @ResponseBody
    public BaseResponse isExistByUserName(@RequestBody String json) {

        try {

            String message = "";

            JSONObject param = JSONObject.parseObject(json);

            String userName = param.getString("userName");

            //非空校验
            if (StringUtils.isEmpty(userName)) {
                message = "用户名不能为空！";
            }

            if (!"".equals(message)) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, message);
            }


            if (userService.isExistByUserName(userName) > 0) {
                return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "用户名已存在！");
            }

            return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, null, "用户名可以使用！");

        } catch (Exception e) {

            log.error("[用户服务] 校验用户名是否重复接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

    /**
     * 是否登陆过
     *
     * @return
     */
    @PostMapping("/isLogined")
    @ResponseBody
    @TokenRequired
    public BaseResponse isLogined() {
        return BaseResponseBuilder.buildBaseResponse(ReturnCode.SUCCESS, null, "已登录！");
    }


    /**
     * 查询用户详细信息
     *
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    @TokenRequired
    public BaseResponse get(@RequestParam(name = "userId", required = false) String userId,
                            @RequestParam(name = "userName", required = false) String userName) {

        try {

            User user = new User();
            user.setId(StringUtils.isEmpty(userId) ? 0 : Long.parseLong(userId));
            user.setUserName(userName);

            return userService.get(user);

        } catch (Exception e) {

            log.error("[用户服务] 查询用户详细信息接口异常", e);
            return BaseResponseBuilder.buildBaseResponse(ReturnCode.ERROR, null, "请求失败，请稍后再试！");
        }
    }

}

