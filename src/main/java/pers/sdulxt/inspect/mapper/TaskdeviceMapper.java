package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.TaskdeviceEntity;

import java.util.List;

@Component
public interface TaskdeviceMapper {

    @Select("select * from view_taskdevice where taskId = #{taskId}")
    List<TaskdeviceEntity> getDevicesByTask(int taskId);

    @Insert("insert into taskdevice (`taskId`, `deviceId`) values (#{taskId}, #{deviceId})")
    void insertTaskdevice(@Param("taskId") int taskId, @Param("deviceId") int deviceId);
}
