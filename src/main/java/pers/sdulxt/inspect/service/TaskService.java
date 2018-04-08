package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.mapper.TaskMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.List;

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
     * Get tasks assigned to the user
     * @param assignee Phone number of the user.
     * @return The task list, guarantee not null.
     */
    public List<TaskEntity> getTasks(String assignee){
        return taskMapper.getTasksByAssignee(assignee);
    }

    /**
     * Get tasks count assigned to the user by tasks' state.
     * @param assignee Phone number of the user.
     * @param state The query state.
     * @return The count of tasks.
     */
    public int getTasksCount(String assignee, TaskEntity.State state){
        return taskMapper.getTasksCountByState(assignee, state.toString());
    }
}
