package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.TaskEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface TaskMapper {

    @Select("select * from view_task where assignee = #{assignee} and state = #{state}")
    List<TaskEntity> getTasksByAssignee(@Param("assignee") String assignee, @Param("state") String state);

    @Select("select state, count(*) count from task where assignee = #{assignee} group by state")
    List<Map<String, Object>> getTaskCountByAssignee(@Param("assignee") String assignee);

    @Select("select assignee from task where id = #{id}")
    String getAssigneeById(@Param("id") int id);

    @Update("update task set title=#{title}, description=#{description}, description=#{description}, assignee=#{assignee}, state=#{state}, dueTime=#{dueTime} where id=#{id}")
    void updateTask(TaskEntity taskEntity);

    @Update("update task set state=#{state} where id=#{id}")
    void updateState(@Param("id") int id, @Param("state") String state);

    @Update("update task set startTime=#{startTime} where id=#{id}")
    void updateStartTime(@Param("id") int id, @Param("startTime") Date startTime);

    @Update("update task set endTime=#{endTime} where id=#{id}")
    void updateEndTime(@Param("id") int id, @Param("endTime") Date endTime);

    @Update("update task set assignee=#{assignee} where id=#{id}")
    void updateAssignee(@Param("id") int id, @Param("assignee") String assignee);

    @Insert("insert into task (`title`, `description`, `assignee`, `dueTime`, `creator`) values (#{title}, #{description}, #{assignee}, #{dueTime}, #{creator})")
    @Options(useGeneratedKeys = true)
    void insertTask(TaskEntity task);
}
