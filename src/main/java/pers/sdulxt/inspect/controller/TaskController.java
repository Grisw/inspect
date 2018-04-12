package pers.sdulxt.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.sdulxt.inspect.entity.TaskEntity;
import pers.sdulxt.inspect.model.Response;
import pers.sdulxt.inspect.service.TaskService;
import pers.sdulxt.inspect.service.TokenService;
import pers.sdulxt.inspect.util.ValidateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        if(tokenService.checkValidity(pn, token))
            return new Response<>(taskService.updateTaskState(id, pn, state));
        else
            return new Response<>(Response.Code.TOKEN_EXPIRED);
    }

    @PutMapping
    public Response<Integer> createTask(@RequestBody Map<String, Object> params, @RequestHeader("X-INSPECT-PN") String pn, @RequestHeader("X-INSPECT-TOKEN") String token){
        String title = (String) params.get("title");
        String description = (String) params.get("description");
        String assignee = (String) params.get("assignee");
        Date dueTime = null;
        try {
            dueTime = params.get("dueTime") == null ? null : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).parse((String) params.get("dueTime"));
        } catch (ParseException e) {
            return new Response<>(Response.Code.PARAMS_ERROR);
        }
        @SuppressWarnings("unchecked")
        List<Integer> devices = (List<Integer>) params.get("devices");

        return new Response<>(Response.Code.SUCCESS);
    }
}
