package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.sdulxt.inspect.entity.UserEntity;
import pers.sdulxt.inspect.mapper.UserMapper;
import pers.sdulxt.inspect.model.Constant;
import pers.sdulxt.inspect.model.Response;

import java.util.List;

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

    public List<UserEntity> getJunior(String phoneNumber){
        return userMapper.getJuniorByLeader(phoneNumber);
    }

    @Transactional
    public Response.Code updateEmail(String pn, String email){
        userMapper.updateEmail(pn, email);
        return Response.Code.SUCCESS;
    }
}
