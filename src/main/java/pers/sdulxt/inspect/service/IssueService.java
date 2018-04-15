package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.sdulxt.inspect.entity.IssueEntity;
import pers.sdulxt.inspect.mapper.IssueMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.List;

@Service
public class IssueService {
    private final IssueMapper issueMapper;
    private Log log;

    @Autowired
    public IssueService(IssueMapper issueMapper) {
        this.issueMapper = issueMapper;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    public List<IssueEntity> getIssuesByTaskDevice(int taskId, int deviceId){
        return issueMapper.getIssuesByTaskDevice(taskId, deviceId);
    }

    @Transactional
    public int createIssue(int deviceId, Integer taskId, String title, String description, String picture, String pn){
        IssueEntity entity = new IssueEntity();
        entity.setDeviceId(deviceId);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setTaskId(taskId);
        entity.setPicture(picture);
        entity.setCreator(pn);

        issueMapper.insertIssue(entity);
        return entity.getId();
    }
}
