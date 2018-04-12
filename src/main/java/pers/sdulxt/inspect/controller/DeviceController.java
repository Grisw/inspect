package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.sdulxt.inspect.entity.DeviceEntity;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.DeviceService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final TokenService tokenService;

    @Autowired
    public DeviceController(DeviceService deviceService, TokenService tokenService) {
        this.deviceService = deviceService;
        this.tokenService = tokenService;
    }

    @GetMapping("/all")
    public Response<List<DeviceEntity>> getDevices(@RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(deviceService.getDevices());
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }
}
