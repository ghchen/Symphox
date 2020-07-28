package com.symphox.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.symphox.dto.DepartmentDto;
import com.symphox.dto.RestResponse;
import com.symphox.entity.Department;
import com.symphox.error.ModelViolatedException;
import com.symphox.error.ResourceNotFoundException;
import com.symphox.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository dr;

    public DepartmentDto create(DepartmentDto form) {
        if (findById(form.getId()).isPresent()) {
            throw new ModelViolatedException("Department id duplicate!");
        }
        Department entity = new Department();
        entity.setId(form.getId());
        entity.setEnabled(true);
        entity.setFullname(form.getFullname());
        entity = dr.save(entity);
        return entity.toDto();
    }

    public DepartmentDto update(DepartmentDto form) {
        Department entity = findById(form.getId()).orElseThrow(ResourceNotFoundException::new);
        entity.setFullname(form.getFullname());
        entity = dr.save(entity);
        return entity.toDto();
    }

    public Optional<Department> findById(String id) {
        Assert.notNull(id, "id is null");
        return dr.findById(id);
    }

    public DepartmentDto fetch(String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "id is empty!");
        Department entity = findById(id).orElseThrow(ResourceNotFoundException::new);
        return entity.toDto();
    }

    public RestResponse disable(String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "id is empty!");
        Department entity = findById(id).orElseThrow(ResourceNotFoundException::new);
        entity.setEnabled(false);
        entity = dr.save(entity);
        return new RestResponse();
    }

    public RestResponse enabled(String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "id is empty!");
        Department entity = findById(id).orElseThrow(ResourceNotFoundException::new);
        entity.setEnabled(true);
        entity = dr.save(entity);
        return new RestResponse();
    }

}
