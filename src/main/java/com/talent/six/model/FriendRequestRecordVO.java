package com.talent.six.model;

import lombok.Data;

/**
 * 好友申请记录VO
 */
@Data
public class FriendRequestRecordVO {

    private long id;                        //id

    private String userId;                  //用户ID

    private String targetId;                //目标ID

    private String nickName;                //用户昵称

    private String status;                  //状态 0：待确认，1：确认，2：忽略

    private String message;                 //申请内容
    
    private String avatar;                  //头像url

}
