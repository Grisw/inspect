package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.mapper.TaskMapper;
import pers.sdulxt.inspect.model.Constant;
import pers.sdulxt.inspect.model.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskdeviceService taskdeviceService;
    private Log log;

    @Autowired
    public TaskService(TaskMapper taskMapper, TaskdeviceService taskdeviceService) {
        this.taskMapper = taskMapper;
        this.taskdeviceService = taskdeviceService;
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
            entity.setDevices(taskdeviceService.getTaskdevicesByTask(entity.getId()));
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

    @Transactional
    public Response.Code updateTaskState(int id, String pn, TaskEntity.State state) {
        if(taskMapper.getAssigneeById(id).equals(pn)){
            taskMapper.updateState(id, state.toString());
            switch (state){
                case D:
                    taskMapper.updateStartTime(id, new Date());
                    break;
                case E:
                    taskMapper.updateEndTime(id, new Date());
                    break;
            }
            return Response.Code.SUCCESS;
        }else{
            return Response.Code.ACCESS_REJECT;
        }
    }

    @Transactional
    public int createTask(String title, String description, String assignee, Date dueTime, List<Integer> devices, String pn){
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setDescription(description);
        task.setAssignee(assignee);
        task.setDueTime(dueTime);
        task.setCreator(pn);

        taskMapper.insertTask(task);
        for(int device : devices){
            taskdeviceService.createTaskDevice(task.getId(), device);
        }
        return task.getId();
    }

    @Transactional
    public Date updateTaskDevice(int taskId, int deviceId, boolean checked, String picture) {
        return taskdeviceService.updateTaskDevice(taskId, deviceId, checked, picture);
    }
}
