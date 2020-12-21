package com.talent.six.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
public class User {

    private long id;                //id

    private String userName;        //用户名

    private String password;        //密码

    private String nickName;        //昵称

    private String gender;          //性别（0:女，1:男，2:保密）

    private String status;          //状态（0：禁用，1：启用）

    private String avatar;          //头像

    private LocalDateTime createTime;        //创建时间

    private LocalDateTime modifyTime;        //更新时间


    public static final class Builder {
        private long id;                //id
        private String userName;        //用户名
        private String password;        //密码
        private String nickName;        //昵称
        private String gender;          //性别（0:女，1:男，2:保密）
        private String status;          //状态（0：禁用，1：启用）
        private String avatar;          //头像
        private LocalDateTime createTime;        //创建时间
        private LocalDateTime modifyTime;        //更新时间

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder modifyTime(LocalDateTime modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUserName(userName);
            user.setPassword(password);
            user.setNickName(nickName);
            user.setGender(gender);
            user.setStatus(status);
            user.setAvatar(avatar);
            user.setCreateTime(createTime);
            user.setModifyTime(modifyTime);
            return user;
        }
    }
}
