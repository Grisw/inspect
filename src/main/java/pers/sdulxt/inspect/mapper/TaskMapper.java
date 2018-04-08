package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.TaskEntity;

import java.util.List;

@Component
public interface TaskMapper {

    @Select("select * from task where assignee = #{assignee}")
    List<TaskEntity> getTasksByAssignee(String assignee);

    @Select("select count(*) from task where assignee = #{assignee} and state = #{state}")
    int getTasksCountByState(@Param("assignee") String assignee, @Param("state") String state);
}
