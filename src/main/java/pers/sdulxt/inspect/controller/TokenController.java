package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
            return new Response<>(Response.Code.PARAMS_REQUIRED);
        }

        // Processing
        String token = tokenService.login(phoneNumber, password);
        if(token == null)
            return new Response<>(Response.Code.WRONG_CREDENTIALS);
        else
            return new Response<>(token);
    }

}
