package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.entity.DeviceEntity;
import pers.sdulxt.inspect.mapper.DeviceMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceMapper deviceMapper;
    private Log log;

    @Autowired
    public DeviceService(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    public List<DeviceEntity> getDevices(){
        return deviceMapper.getAllDevices();
    }
}
