package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.TaskEntity;

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

    @Update("update task set assignee=#{assignee} where id=#{id}")
    void updateAssignee(@Param("id") int id, @Param("assignee") String assignee);
}
