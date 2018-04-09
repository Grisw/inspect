package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.mapper.TaskMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskMapper taskMapper;
    private Log log;

    @Autowired
    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    /**
     * Get tasks assigned to the user by tasks' state.
     * @param assignee Phone number of the user.
     * @param state The query state.
     * @return The task list, guarantee not null.
     */
    public List<TaskEntity> getTasks(String assignee, TaskEntity.State state){
        return taskMapper.getTasksByAssignee(assignee, state.toString());
    }

    /**
     * Get tasks count assigned to the user.
     * @param assignee Phone number of the user.
     * @return The count of tasks in each state.
     */
    public Map<TaskEntity.State, Long> getTasksCount(String assignee){
        List<Map<String, Object>> data = taskMapper.getTaskCountByAssignee(assignee);
        Map<TaskEntity.State, Long> result = new HashMap<>();
        for (Map<String, Object> row : data){
            result.put(TaskEntity.State.valueOf((String) row.get("state")), (Long) row.get("count"));
        }
        for(TaskEntity.State state : TaskEntity.State.values()){
            if(!result.containsKey(state)){
                result.put(state, 0L);
            }
        }
        return result;
    }
}
