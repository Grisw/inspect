package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.TaskService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public Response<List<TaskEntity>> getTasks(@RequestParam String assignee, @RequestParam TaskEntity.State state, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(assignee, state)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(taskService.getTasks(assignee, state));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @GetMapping("/count")
    public Response<Map<TaskEntity.State, Long>> getTasksCount(@RequestParam String assignee, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        // Validating
        if(ValidateUtils.checkNull(assignee)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token))
            return new Response<>(taskService.getTasksCount(assignee));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PostMapping("/state")
    public Response<Void> updateTaskState(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        Integer id;
        TaskEntity.State state;
        try {
            id = (int) params.get("id");
            state = TaskEntity.State.valueOf((String) params.get("state"));
        }catch (Exception e){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }

        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(taskService.updateTaskState(id, pn, state));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PutMapping
    public Response<Integer> createTask(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String assignee = (String) params.get("assignee");
        Date dueTime;
        try {
            dueTime = params.get("dueTime") == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse((String) params.get("dueTime"));
        } catch (ParseException e) {
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        @SuppressWarnings("unchecked")
        List<Integer> devices = (List<Integer>) params.get("devices");

        // Validating
        if(ValidateUtils.checkNull(title, description, assignee, devices)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(taskService.createTask(title, description, assignee, dueTime, devices, pn));
            }catch (DataAccessException e){
                e.printStackTrace();
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PostMapping("/device")
    public Response<Date> updateTaskDevice(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        Boolean checked = (Boolean) params.get("checked");
        Integer taskId = (Integer) params.get("taskId");
        Integer deviceId = (Integer) params.get("deviceId");
        String picture = (String) params.get("picture");

        // Validating
        if(ValidateUtils.checkNull(checked, taskId, deviceId)){
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        if(ValidateUtils.checkNull(pn, token)){
            return new Response<>(Response.Code.TOKEN_EXPIRED);
        }

        // Check token
        if(tokenService.checkValidity(pn, token)){
            try{
                return new Response<>(taskService.updateTaskDevice(taskId, deviceId, checked, picture));
            }catch (DataAccessException e){
                return new Response<>(Response.Code.RESOURCE_NOT_FOUND);
            }
        }
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

}
