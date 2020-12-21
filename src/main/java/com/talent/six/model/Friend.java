package com.talent.six.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 好友信息
 */
@Data
public class Friend {

    private long id;                        //id

    private String userId;                  //用户ID

    private String friendId;                //好友ID

    private String status;                  //状态 0：拉黑，1：正常，-1：删除

    private LocalDateTime createTime;       //创建时间

    private LocalDateTime modifyTime;       //更新时间


}
