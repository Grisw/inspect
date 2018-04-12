package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.DeviceEntity;

import java.util.List;

@Component
public interface DeviceMapper {

    @Select("select * from device where id = #{id}")
    DeviceEntity getDeviceById(int id);

    @Select("select * from device")
    List<DeviceEntity> getAllDevices();
}
