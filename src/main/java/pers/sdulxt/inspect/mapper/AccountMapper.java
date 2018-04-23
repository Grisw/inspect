package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.AccountEntity;

@Component
public interface AccountMapper {

    @Select("select * from account where phoneNumber = #{phoneNumber}")
    AccountEntity getAccountByPhoneNumber(String phoneNumber);

    @Update("update account set password=#{password} where phoneNumber = #{phoneNumber}")
    void changePassword(@Param("phoneNumber") String phoneNumber, @Param("password") String password);

}
