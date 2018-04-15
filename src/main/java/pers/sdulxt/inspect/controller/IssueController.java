package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.IssueService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.util.Map;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;
    private final TokenService tokenService;

    @Autowired
    public IssueController(IssueService issueService, TokenService tokenService) {
        this.issueService = issueService;
        this.tokenService = tokenService;
    }

    @PutMapping
    public Response<Integer> createIssue(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        int deviceId = (int) params.get("deviceId");
        Integer taskId = (Integer) params.get("taskId");
        String picture = (String) params.get("picture");

        // Validating
        if(ValidateUtils.checkNull(title, description, taskId)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(issueService.createIssue(deviceId, taskId, title, description, picture, pn));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

}
