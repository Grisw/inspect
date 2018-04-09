package pers.sdulxt.inspect.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.sdulxt.inspect.entity.TaskEntity;

import java.util.List;
import java.util.Map;

@Component
public interface TaskMapper {

    @Select("select a.*, b.name creatorName, c.name assigneeName from task a left outer join user b on a.creator = b.phoneNumber left outer join user c on a.assignee = c.phoneNumber where a.assignee = #{assignee} and a.state = #{state}")
    List<TaskEntity> getTasksByAssignee(@Param("assignee") String assignee, @Param("state") String state);

    @Select("select state, count(*) count from task where assignee = #{assignee} group by state")
    List<Map<String, Object>> getTaskCountByAssignee(@Param("assignee") String assignee);
}
