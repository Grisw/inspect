package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ContactMapper {

    @Select("select `to` from contact where `from` = #{phoneNumber}")
    List<String> getContactsByPhoneNumber(String phoneNumber);

}
