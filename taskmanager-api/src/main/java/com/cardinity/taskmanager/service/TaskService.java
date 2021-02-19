package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.dao.TaskDAO;
import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.Task;
import com.cardinity.taskmanager.util.TaskUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Service
public class TaskService {
    private TaskDAO taskRepository;
    private TaskUtil taskUtil;
    private ProjectService projectService;

    @Autowired
    public TaskService(TaskDAO taskDAO, TaskUtil taskUtil, ProjectService projectService) {
        this.taskRepository = taskDAO;
        this.taskUtil = taskUtil;
        this.projectService = projectService;
    }


    public TaskDto createTask(TaskDto taskDto) {
        if (StringUtils.isEmpty(StringUtils.trim(taskDto.getTaskDescription()))) {
            throw new InvalidTaskException("task description is empty");
        }
        Project p;
        try {
            p = this.projectService.getProject(taskDto.getProject());
        } catch (NoSuchElementException ex) {
            throw new InvalidTaskException("associated project not found for the task");
        } catch (InvalidDataAccessApiUsageException ex) {
            throw new InvalidTaskException("associated project id must not be null");
        }

        Task task = taskUtil.convertDtoToEntity(taskDto);
        task.setProject(p);
        task = this.taskRepository.save(task);
        taskDto = taskUtil.convertEntityToDto(task);
        return taskDto;
    }
}
