package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.AccountEntity;

@Component
public interface AccountMapper {

    @Select("select * from account where phoneNumber = #{phoneNumber}")
    AccountEntity getAccountByPhoneNumber(String phoneNumber);
}
