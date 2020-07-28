package com.symphox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.symphox.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, String> {

}
