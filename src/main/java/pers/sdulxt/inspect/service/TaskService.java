package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.mapper.TaskMapper;
import pers.sdulxt.inspect.mapper.TaskdeviceMapper;
import pers.sdulxt.inspect.model.Constant;
import pers.sdulxt.inspect.model.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskdeviceMapper taskdeviceMapper;
    private Log log;

    @Autowired
    public TaskService(TaskMapper taskMapper, TaskdeviceMapper taskdeviceMapper) {
        this.taskMapper = taskMapper;
        this.taskdeviceMapper = taskdeviceMapper;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    /**
     * Get tasks assigned to the user by tasks' state with task devices.
     * @param assignee Phone number of the user.
     * @param state The query state.
     * @return The task list, guarantee not null.
     */
    public List<TaskEntity> getTasks(String assignee, TaskEntity.State state){
        List<TaskEntity> entities = taskMapper.getTasksByAssignee(assignee, state.toString());
        for(TaskEntity entity : entities){
            entity.setDevices(taskdeviceMapper.getDevicesByTask(entity.getId()));
        }
        return entities;
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

    public Response.Code updateTaskState(int id, String pn, TaskEntity.State state){
        if(taskMapper.getAssigneeById(id).equals(pn)){
            try{
                taskMapper.updateState(id, state.toString());
                return Response.Code.SUCCESS;
            }catch (DataAccessException e){
                return Response.Code.RESOURCE_NOT_FOUND;
            }
        }else{
            return Response.Code.ACCESS_REJECT;
        }
    }

}
