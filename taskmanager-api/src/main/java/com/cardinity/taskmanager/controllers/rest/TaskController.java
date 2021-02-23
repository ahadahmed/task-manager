package com.cardinity.taskmanager.controllers.rest;

import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Task;
import com.cardinity.taskmanager.service.TaskService;
import com.cardinity.taskmanager.util.TaskUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Tag(name = "Rest API for Tasks")
@RestController
@RequestMapping("/api/v1")
@Validated
public class TaskController {
    private TaskService taskService;
    private TaskUtil taskUtil;

    @Autowired
    public TaskController(TaskService taskService, TaskUtil taskUtil) {
        this.taskService = taskService;
        this.taskUtil = taskUtil;
    }

    @Operation(summary = "Get Task", description = "API for getting a Task information")
    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))
            , responseCode = "200"
            , description = "Success response")

    @ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)
            , examples = @ExampleObject(
            name = "Task not found with taskId 100"
            , value = "{\n" +
            "  \"status\": \"NOT_FOUND\",\n" +
            "  \"error\": \"task not found with id 100\",\n" +
            "  \"errors\": []\n" +
            "}"
    )

    )}, responseCode = "404"
            , description = "Task not found with taskId")

    @JsonView(value = View.TaskResponseView.class)
    @GetMapping("/task/{taskId}")
    public TaskDto getTask(@PathVariable long taskId) {
        Task task = this.taskService.getTaskById(taskId);
        TaskDto taskDto = this.taskUtil.convertEntityToDto(task);
        return taskDto;
    }

    @JsonView(value = View.TaskResponseView.class)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/task/user/{userId}")
    public List<TaskDto> getUserTasks(@PathVariable long userId) {
        List<TaskDto> tasks = this.taskService.getAllTaskByUserId(userId);
        return tasks;
    }


    @GetMapping("/task")
    @JsonView(value = {View.TaskResponseView.class})
    public List<TaskDto> getTasks() {
        List<Task> tasks = this.taskService.getTasks();
        List<TaskDto> t = this.taskUtil.convertEntityToDtoList(tasks);
        return t;
    }


    @Operation(summary = "Create New Task", description = "API for creating a New Task")
    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))
            , responseCode = "201"
            , description = "Task Created Successfully.")

    @ApiResponse(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)
            , examples = @ExampleObject(
            name = "erroresponse"
            , value = "{\n" +
            "  \"status\": \"BAD_REQUEST\",\n" +
            "  \"error\": \"task description is empty\",\n" +
            "  \"errors\": []\n" +
            "}"


    )

    )}, responseCode = "400"
            , description = "Empty task description.")

    @PostMapping("/task")
    @JsonView(value = View.TaskResponseView.class)
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(
            @RequestBody
            @JsonView(value = View.HttpMethodView.POST.class)
                    TaskDto taskDto) {
        TaskDto response = this.taskService.createTask(taskDto);
        return response;
    }

    @PutMapping("/task/{taskId}")
    @Deprecated
    public TaskDto updateTask(@PathVariable long taskId,
                              @RequestBody
                              @JsonView(value = View.HttpMethodView.PUT.class)
                                      TaskDto taskDto) {
        TaskDto updatedTask = this.taskService.updateTaskStatus(taskId, taskDto.getTaskStatus());
        return updatedTask;
    }

    @PutMapping("/task/{taskId}/assign")
    @JsonView(value = View.TaskResponseView.class)
    public TaskDto assignTaskToUser(@PathVariable long taskId,
                                    @RequestBody
                                    @JsonView(value = View.HttpMethodView.PUT.class)
                                            TaskDto taskDto) {
        TaskDto updatedTask = this.taskService.updateTask(taskDto);
        return updatedTask;
    }
}
