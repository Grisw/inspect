package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.sdulxt.inspect.entity.DeviceEntity;
import pers.sdulxt.inspect.mapper.DeviceMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceMapper deviceMapper;
    private final IssueService issueService;
    private Log log;

    @Autowired
    public DeviceService(DeviceMapper deviceMapper, IssueService issueService) {
        this.deviceMapper = deviceMapper;
        this.issueService = issueService;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    public List<DeviceEntity> getDevices(){
        List<DeviceEntity> devices = deviceMapper.getAllDevices();
        for (DeviceEntity deviceEntity : devices){
            deviceEntity.setIssues(issueService.getIssuesByDevice(deviceEntity.getId()));
        }
        return devices;
    }

    @Transactional
    public int createDevice(String name, String description, double latitude, double longitude){
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setName(name);
        deviceEntity.setDescription(description);
        deviceEntity.setLatitude(latitude);
        deviceEntity.setLongitude(longitude);

        deviceMapper.insertDevice(deviceEntity);
        return deviceEntity.getId();
    }

}
