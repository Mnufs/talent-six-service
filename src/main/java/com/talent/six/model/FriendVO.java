package com.talent.six.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 好友信息VO
 */
@Data
public class FriendVO {

    private String friendId;                //好友ID

    private String friendName;              //好友昵称

    private String avatar;                  //头像url

}
