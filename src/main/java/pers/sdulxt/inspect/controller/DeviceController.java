package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.entity.DeviceEntity;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.DeviceService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.util.List;
import java.util.Map;

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

    @PutMapping
    public Response<Integer> createDevice(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String name = (String) params.get("name");
        String description = (String) params.get("description");
        Double latitude = (Double) params.get("latitude");
        Double longitude = (Double) params.get("longitude");

        // Validating
        if(ValidateUtils.checkNull(name, description, latitude, longitude)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(deviceService.createDevice(name, description, latitude, longitude));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

}
