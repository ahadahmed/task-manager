package com.cardinity.taskmanager.util;

import com.cardinity.taskmanager.dto.ProjectDTO;
import com.cardinity.taskmanager.entity.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Component
public class ProjectUtil {
    private ModelMapper modelMapper;

    @Autowired
    public ProjectUtil(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public ProjectDTO convertEntityToDto(Project project){
        return this.modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertDtoToEntity(ProjectDTO projectDTO){
        return this.modelMapper.map(projectDTO, Project.class);
    }
}
