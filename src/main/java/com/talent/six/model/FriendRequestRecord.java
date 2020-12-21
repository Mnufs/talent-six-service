package com.talent.six.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 好友申请记录表
 */
@Data
public class FriendRequestRecord {

    private long id;                        //id

    private String userId;                  //用户ID

    private String targetId;                //目标ID

    private String status;                  //状态 0：待确认，1：确认，2：忽略

    private String message;                 //申请内容

    private LocalDateTime createTime;       //创建时间

    private LocalDateTime modifyTime;       //更新时间


    public static final class Builder {
        private long id;                        //id
        private String userId;                  //用户ID
        private String targetId;                //目标ID
        private String status;                  //状态 0：待确认，1：确认，2：忽略
        private String message;                 //申请内容
        private LocalDateTime createTime;       //创建时间
        private LocalDateTime modifyTime;       //更新时间

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder targetId(String targetId) {
            this.targetId = targetId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
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

        public FriendRequestRecord build() {
            FriendRequestRecord friendRequestRecord = new FriendRequestRecord();
            friendRequestRecord.setId(id);
            friendRequestRecord.setUserId(userId);
            friendRequestRecord.setTargetId(targetId);
            friendRequestRecord.setStatus(status);
            friendRequestRecord.setMessage(message);
            friendRequestRecord.setCreateTime(createTime);
            friendRequestRecord.setModifyTime(modifyTime);
            return friendRequestRecord;
        }
    }
}
