package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.UserEntity;

@Component
public interface UserMapper {

    @Select("select * from user where phoneNumber = #{phoneNumber}")
    UserEntity getUserByPhoneNumber(String phoneNumber);

}
