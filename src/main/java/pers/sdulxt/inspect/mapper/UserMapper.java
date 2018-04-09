package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.UserEntity;

@Component
public interface UserMapper {

    @Select("select a.*, b.name leaderName from user a left outer join user b on a.leader = b.leader where a.phoneNumber = #{phoneNumber}")
    UserEntity getUserByPhoneNumber(String phoneNumber);

    @Select("select name from user where phoneNumber = #{phoneNumber}")
    String getUserNameByPhoneNumber(String phoneNumber);
}
