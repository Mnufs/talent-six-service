package com.talent.six.mapper;

import com.talent.six.model.Friend;
import com.talent.six.model.FriendRequestRecord;
import com.talent.six.model.FriendRequestRecordVO;
import com.talent.six.model.FriendVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper {

    /**
     * 是否是好友
     * 根据userId和friendId查询是否存在
     *
     * @param userId    用户ID
     * @param friendId  好友ID
     *
     * @return
     * @throws Exception
     */
    int isFriend(@Param("userId") String userId,
                 @Param("friendId") String friendId) throws Exception;

    /**
     * 查询好友列表
     *
     * @param userId    用户ID
     * @return
     */
    List<FriendVO> listFriend(@Param("userId") String userId)  throws Exception;

    /**
     * 查询好友申请记录
     *
     * @param userId    用户ID
     * @return
     */
    List<FriendRequestRecordVO> listFriendRequestRecord(@Param("userId") String userId)  throws Exception;

    /**
     * 保存好友申请记录
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    int saveFriendRequestRecord(FriendRequestRecord friendRequestRecord) throws Exception;

    /**
     * 获取未读的好友申请数量
     *
     * @param userId
     * @return
     * @throws Exception
     */
    int getUnreadFriendRequestCount(@Param("userId") String userId) throws Exception;

    /**
     * 根据ID获取好友申请记录
     *
     * @param id
     * @return
     */
    FriendRequestRecordVO getFriendRequestRecordByID(@Param("id") long id) throws Exception;

    /**
     * 更新好友申请状态
     *
     * @param friendRequestRecord
     * @return
     * @throws Exception
     */
    int updateFriendRequestStatus(FriendRequestRecord friendRequestRecord) throws Exception;

    /**
     * 批量保存好友信息
     *
     * @param friendList
     * @return
     * @throws Exception
     */
    int batchSaveFriend(@Param("friendList") List<Friend> friendList) throws Exception;
}
