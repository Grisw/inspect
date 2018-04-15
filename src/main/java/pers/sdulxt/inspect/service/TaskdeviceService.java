package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.sdulxt.inspect.entity.TaskdeviceEntity;
import pers.sdulxt.inspect.mapper.TaskdeviceMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.Date;
import java.util.List;

@Service
public class TaskdeviceService {
    private final TaskdeviceMapper taskdeviceMapper;
    private final IssueService issueService;
    private Log log;

    @Autowired
    public TaskdeviceService(TaskdeviceMapper taskdeviceMapper, IssueService issueService) {
        this.taskdeviceMapper = taskdeviceMapper;
        this.issueService = issueService;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    public List<TaskdeviceEntity> getTaskdevicesByTask(int taskId){
        List<TaskdeviceEntity> taskdevices = taskdeviceMapper.getDevicesByTask(taskId);
        for (TaskdeviceEntity taskdeviceEntity : taskdevices){
            taskdeviceEntity.setIssues(issueService.getIssuesByTaskDevice(taskId, taskdeviceEntity.getDeviceId()));
        }
        return taskdevices;
    }

    @Transactional
    public Date updateTaskDevice(int taskId, int deviceId, boolean checked, String picture) {
        Date date = new Date();
        taskdeviceMapper.updateTaskDevice(taskId, deviceId, checked, picture, date);
        return date;
    }

    @Transactional
    public void createTaskDevice(int taskId, int deviceId){
        taskdeviceMapper.insertTaskdevice(taskId, deviceId);
    }
}
