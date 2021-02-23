package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.dto.ProjectDTO;
import com.cardinity.taskmanager.dao.ProjectDao;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.ProjectStatus;
import com.cardinity.taskmanager.entity.User;
import com.cardinity.taskmanager.util.ProjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Service
public class ProjectService {

    private ProjectDao projectDao;
    private ProjectUtil projectUtil;

    private UserService userService;

    @Autowired
    public ProjectService(ProjectDao projectDao, ProjectUtil util, UserService userService){
        this.projectDao = projectDao;
        this.projectUtil = util;
        this.userService = userService;
    }

    public ProjectDTO createProject(ProjectDTO projectDTO){
        Project project = this.projectUtil.convertDtoToEntity(projectDTO);
//        project.setCreated(LocalDateTime.now());
        project = this.projectDao.save(project);
        return this.projectUtil.convertEntityToDto(project);
    }


    public Project getProject(Long projectId) throws NoSuchElementException{
        Optional<Project> project = this.projectDao.findById(projectId);
        if(project.isEmpty()){
            throw new NoSuchElementException("project not found with id: " + projectId);
        }
        return project.get();
    }

    public List<ProjectDTO> getProjectsByUser(long userId){
        User user = this.userService.getUserById(userId);
        List<Project> projectEntity = this.projectDao.findAllByUsers(user);
//        List<ProjectDTO> projects = this.projectUtil.convertEntityToDtoList(user.getProjects());
        List<ProjectDTO> projects = this.projectUtil.convertEntityToDtoList(user.getProjects());

        return projects;
    }

    public List<ProjectDTO> getAll(){
        List<Project> projects = (List<Project>) this.projectDao.findAll();
//        List<ProjectDTO> p = this.projectUtil.convertEntityToDtoList(Set.copyOf(projects));
        List<ProjectDTO> p = this.projectUtil.convertEntityToDtoList(projects);
        return p;
    }

    public ProjectDTO deleteProject(Long id){
       Project project = this.getProject(id);
       project.setProjectStatus(ProjectStatus.DELETED);
       project =this.projectDao.save(project);
       ProjectDTO projectDTO = this.projectUtil.convertEntityToDto(project);
       return projectDTO;
    }

}
