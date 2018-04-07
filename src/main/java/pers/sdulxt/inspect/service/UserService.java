package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.entity.UserEntity;
import pers.sdulxt.inspect.mapper.UserMapper;
import pers.sdulxt.inspect.model.Constant;

@Service
public class UserService {
    private final UserMapper userMapper;
    private Log log;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    /**
     * Get user entity.
     * @param phoneNumber Phone number.
     * @return The user object, null if user not found.
     */
    public UserEntity getUser(String phoneNumber){
        return userMapper.getUserByPhoneNumber(phoneNumber);
    }
}
