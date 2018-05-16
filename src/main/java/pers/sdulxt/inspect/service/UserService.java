package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.sdulxt.inspect.entity.AccountEntity;
import pers.sdulxt.inspect.entity.UserEntity;
import pers.sdulxt.inspect.mapper.AccountMapper;
import pers.sdulxt.inspect.mapper.UserMapper;
import pers.sdulxt.inspect.model.Constant;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.util.MD5Utils;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private Log log;

    @Autowired
    public UserService(UserMapper userMapper, AccountMapper accountMapper) {
        this.userMapper = userMapper;
        this.accountMapper = accountMapper;
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

    @Transactional
    public Response.Code updateBirthday(String pn, Date birthday){
        userMapper.updateBirthday(pn, birthday);
        return Response.Code.SUCCESS;
    }

    @Transactional
    public Response.Code createUser(String name, String phone, UserEntity.Sex sex, Date birthday, String email, String pn){
        UserEntity userEntity = new UserEntity();
        userEntity.setBirthday(birthday);
        userEntity.setEmail(email);
        userEntity.setLeader(pn);
        userEntity.setName(name);
        userEntity.setSex(sex);
        userEntity.setPhoneNumber(phone);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setPhoneNumber(phone);
        accountEntity.setPassword(MD5Utils.getMD5(Constant.DEFAULT_PASSWORD));

        userMapper.insertUser(userEntity);
        accountMapper.insertAccount(accountEntity);
        return Response.Code.SUCCESS;
    }
}
