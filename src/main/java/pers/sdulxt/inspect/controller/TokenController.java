package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public Response<String> token(@RequestBody Map<String, String> params){
        String phoneNumber = params.get("phoneNumber");
        String password = params.get("password");

        // Validating
        if(ValidateUtils.checkNull(phoneNumber, password)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }

        // Processing
        String token = tokenService.login(phoneNumber, password);
        if(token == null)
            return new Response<>(Response.Code.WRONG_CREDENTIALS);
        else
            return new Response<>(token);
    }

    @DeleteMapping
    public Response<Void> delete(@RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(tokenService.deleteToken(pn));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PostMapping("/password")
    public Response<Void> changePassword(@RequestBody Map<String, String> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String oldPassword = params.get("oldPassword");
        String password = params.get("password");

        // Validating
        if(ValidateUtils.checkNull(oldPassword, password)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(tokenService.changePassword(pn, password, oldPassword));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }
}
