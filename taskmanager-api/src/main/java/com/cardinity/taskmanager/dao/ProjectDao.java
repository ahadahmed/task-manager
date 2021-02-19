package com.cardinity.taskmanager.dao;

import com.cardinity.taskmanager.entity.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectDao extends CrudRepository<Project, Long> {
}
