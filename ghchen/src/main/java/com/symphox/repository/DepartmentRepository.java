package com.symphox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.symphox.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

}
