package com.talent.six.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
public class UserVO {

    private long id;                //id

    private String userName;        //用户名

    private String nickName;        //昵称

    private String gender;          //性别（0:女，1:男，2:保密）

    private String avatar;          //头像

}
