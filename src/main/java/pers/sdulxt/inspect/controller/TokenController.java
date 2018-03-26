package pers.sdulxt.inspect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.sdulxt.inspect.model.Response;

@RestController
@RequestMapping("/token")
public class TokenController {

    @RequestMapping("/hello")
    public Response<String> hello(){
        return new Response<>(Response.Code.SUCCESS, "bilibili!");
    }
}
