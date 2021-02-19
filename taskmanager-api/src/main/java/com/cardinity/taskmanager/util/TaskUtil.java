package com.cardinity.taskmanager.util;

import com.cardinity.taskmanager.controllers.rest.ProjectDTO;
import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Component
public class TaskUtil {
    private ModelMapper modelMapper;

    @Autowired
    public TaskUtil(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public TaskDto convertEntityToDto(Task task){
      TaskDto taskDto =  this.modelMapper.typeMap(Task.class, TaskDto.class).addMappings(mapper -> {
//            mapper.map(source -> source.getTaskDescription(), TaskDto::setTaskDescription);
//            mapper.map(source -> source.getId(), TaskDto::setId);
            mapper.map(source ->source.getProject().getId(), TaskDto::setProject);
//            mapper.map(source -> source.getTaskStatus(), TaskDto::setTaskStatus);
        }).map(task);
//        TaskDto taskDto =
        return taskDto;
    }

    public Task convertDtoToEntity(TaskDto taskDto){
        Task t  = this.modelMapper.map(taskDto, Task.class);
        return t;
    }

}
