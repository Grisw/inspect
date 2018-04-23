package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.UserEntity;

import java.util.List;

@Component
public interface UserMapper {

    @Select("select * from view_user where phoneNumber = #{phoneNumber}")
    UserEntity getUserByPhoneNumber(String phoneNumber);

    @Select("select name from user where phoneNumber = #{phoneNumber}")
    String getUserNameByPhoneNumber(String phoneNumber);

    @Select("SELECT a.*, b.name leaderName FROM user a left outer join user b on a.leader = b.phoneNumber where b.phoneNumber = #{phoneNumber}")
    List<UserEntity> getJuniorByLeader(String phoneNumber);

    @Update("update user set email=#{email} where phoneNumber = #{phoneNumber}")
    void updateEmail(@Param("phoneNumber") String phoneNumber, @Param("email") String email);
}
