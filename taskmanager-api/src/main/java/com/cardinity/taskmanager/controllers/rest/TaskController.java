package com.cardinity.taskmanager.controllers.rest;

import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Task;
import com.cardinity.taskmanager.service.TaskService;
import com.cardinity.taskmanager.util.TaskUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/task/{taskId}")
    public TaskDto getTask(@PathVariable long taskId) {
        Task task = this.taskService.getTaskById(taskId);
        TaskDto taskDto = this.taskUtil.convertEntityToDto(task);
        return taskDto;
    }

    @GetMapping("/task")
    public List<TaskDto> getTasks() {
        List<Task> tasks = this.taskService.getTasks();
        List<TaskDto> t = this.taskUtil.convertEntityToDtoList(tasks);
        return t;
    }

    @PostMapping("/task")
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
    public TaskDto assignTaskToUser(@PathVariable long taskId,
                              @RequestBody
                              @JsonView(value = View.HttpMethodView.PUT.class)
                                      TaskDto taskDto) {
        TaskDto updatedTask = this.taskService.updateTask(taskDto);
        return updatedTask;
    }
}
