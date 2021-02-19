package com.cardinity.taskmanager.dao;

import com.cardinity.taskmanager.entity.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskDAO extends PagingAndSortingRepository<Task, Long> {
}
