package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.entity.UserEntity;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.service.UserService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Response<UserEntity> getUser(@RequestParam String phoneNumber, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(phoneNumber)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(userService.getUser(phoneNumber));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @GetMapping("/junior")
    public Response<List<UserEntity>> getJunior(@RequestParam String phoneNumber, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(phoneNumber)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(userService.getJunior(phoneNumber));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PostMapping("/email")
    public Response<Void> changePassword(@RequestBody Map<String, String> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String email = params.get("email");

        // Validating
        if(ValidateUtils.checkNull(email)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(userService.updateEmail(pn, email));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PostMapping("/birthday")
    public Response<Void> changeBirthday(@RequestBody Map<String, String> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        Date birthday;
        try {
            birthday = params.get("birthday") == null ? null : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(params.get("birthday"));
        } catch (ParseException e) {
            return new Response<>(Response.Code.PARAMS_ERROR);
        }

        // Validating
        if(ValidateUtils.checkNull(birthday)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(userService.updateBirthday(pn, birthday));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PutMapping
    public Response<Void> createUser(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String name = (String) params.get("name");
        String phone = (String) params.get("phone");
        UserEntity.Sex sex;
        try {
            sex = UserEntity.Sex.valueOf((String) params.get("sex"));
        }catch (IllegalArgumentException e){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        Date birthday;
        try {
            birthday = params.get("birthday") == null ? null : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse((String) params.get("birthday"));
        } catch (ParseException e) {
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        String email = params.get("email") == null ? null : (String) params.get("email");

        // Validating
        if(ValidateUtils.checkNull(name, phone, sex)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(userService.createUser(name, phone, sex, birthday, email, pn));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.USER_EXISTS);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }
}
