package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.TaskService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final TokenService tokenService;

    @Autowired
    public TaskController(TaskService taskService, TokenService tokenService) {
        this.taskService = taskService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Response<List<TaskEntity>> getTasks(@RequestParam String assignee, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(assignee)){
            return new Response<>(Response.Code.PARAMS_REQUIRED);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(taskService.getTasks(assignee));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @GetMapping("/count")
    public Response<Integer> getTasksCount(@RequestParam String assignee, @RequestParam TaskEntity.State state, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(assignee, state)){
            return new Response<>(Response.Code.PARAMS_REQUIRED);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(taskService.getTasksCount(assignee, state));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }
}
