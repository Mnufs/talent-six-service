package com.talent.six.mapper;

import com.talent.six.model.User;
import com.talent.six.model.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据用户名称查询是否存在
     *
     * @param userNmae
     * @return
     * @throws Exception
     */
    int isExistByUserName(String userNmae) throws Exception;

    /**
     * 查询用户信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    UserVO get(User user) throws Exception;

    /**
     * 保存
     *
     * @param user
     * @return
     * @throws Exception
     */
    int save(User user) throws Exception;
}
