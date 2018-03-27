package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.sdulxt.inspect.entity.UserEntity;
import pers.sdulxt.inspect.mapper.UserMapper;
import pers.sdulxt.inspect.model.Response;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final UserMapper userMapper;

    @Autowired
    public TokenController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping("/hello")
    public Response<UserEntity> hello(){
        return new Response<>(Response.Code.SUCCESS, userMapper.getUserByPhoneNumber("00000000000"));
    }
}
