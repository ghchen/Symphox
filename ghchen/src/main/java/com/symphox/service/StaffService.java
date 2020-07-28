package com.symphox.service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.symphox.dto.RestResponse;
import com.symphox.dto.StaffDto;
import com.symphox.dto.StaffQueryForm;
import com.symphox.entity.Department;
import com.symphox.entity.Staff;
import com.symphox.error.ModelViolatedException;
import com.symphox.error.ResourceNotFoundException;
import com.symphox.repository.StaffRepository;

@Service
public class StaffService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DepartmentService ds;

    @Autowired
    private StaffRepository sr;

    public Page<StaffDto> findAll(Pageable p) {
        return sr.findAll(p).map(Staff::toDto);
    }

    public Optional<Staff> findById(String id) {
        Assert.notNull(id, "id is null");
        return sr.findById(id);
    }

    public StaffDto create(StaffDto form) {
        if (findById(form.getId()).isPresent()) {
            throw new ModelViolatedException("Staff id duplicate!");
        }
        Instant curr = Instant.now();
        Staff entity = new Staff();
        entity.setId(form.getId());
        entity.setFullname(form.getFullname());
        Optional<Department> dep = ds.findById(form.getDeptId());
        if (!dep.isPresent()) {
            throw new ResourceNotFoundException("Department not found!");
        }
        entity.setDept(dep.get());
        entity.setGender(form.getGender());
        entity.setPhone(form.getPhone());
        entity.setAddress(form.getAddress());
        entity.setAge(form.getAge());
        entity.setCreatedTime(curr);
        entity.setLastUpdated(curr);
        entity.setEnabled(true);
        entity = sr.save(entity);
        return entity.toDto();
    }

    public StaffDto update(StaffDto form) {
        Staff entity = findById(form.getId()).orElseThrow(ResourceNotFoundException::new);
        entity.setFullname(form.getFullname());
        entity.setDept(ds.findById(form.getDeptId()).orElseThrow(ResourceNotFoundException::new));
        entity.setGender(form.getGender());
        entity.setPhone(form.getPhone());
        entity.setAddress(form.getAddress());
        entity.setAge(form.getAge());
        entity.setLastUpdated(Instant.now());
        entity = sr.save(entity);
        return entity.toDto();
    }

    public StaffDto fetch(String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "id is empty!");
        Staff entity = findById(id).orElseThrow(ResourceNotFoundException::new);
        return entity.toDto();
    }

    public RestResponse disable(String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "id is empty!");
        Staff entity = findById(id).orElseThrow(ResourceNotFoundException::new);
        entity.setEnabled(false);
        entity = sr.save(entity);
        return new RestResponse();
    }

    public RestResponse enabled(String id) {
        Assert.isTrue(StringUtils.isNotEmpty(id), "id is empty!");
        Staff entity = findById(id).orElseThrow(ResourceNotFoundException::new);
        entity.setEnabled(true);
        entity = sr.save(entity);
        return new RestResponse();
    }

    public Page<StaffDto> query(StaffQueryForm form, Pageable p) {
        Long count = countStaff(form);
        if (count == null || count <= 0) {
            return new PageImpl<>(Collections.emptyList(), p, 0);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("from Staff s where 1 = 1");
        if (StringUtils.isNotEmpty(form.getFullname())) {
            sb.append(" and s.fullname LIKE :fullname");
        }
        if (StringUtils.isNotEmpty(form.getId())) {
            sb.append(" and s.id LIKE :id");
        }
        if (form.getAge() != null) {
            sb.append(" and s.age = :age");
        }
        if (StringUtils.isNotEmpty(form.getDeptName())) {
            sb.append(" and s.dept.fullname LIKE :deptName");
        }
        Sort sort = p.getSort();
        String jpql = QueryUtils.applySorting(sb.toString(), sort, "s");
        TypedQuery<Staff> q = em.createQuery(jpql, Staff.class);
        if (StringUtils.isNotEmpty(form.getFullname())) {
            q.setParameter("fullname", "%" + form.getFullname() + "%");
        }
        if (StringUtils.isNotEmpty(form.getId())) {
            q.setParameter("id", "%" + form.getId() + "%");
        }
        if (form.getAge() != null) {
            q.setParameter("age", form.getAge());
        }
        if (StringUtils.isNotEmpty(form.getDeptName())) {
            q.setParameter("deptName", "%" + form.getDeptName() + "%");
        }
        int ps = p.getPageSize();
        List<Staff> list = q.setFirstResult((int)p.getOffset()).setMaxResults(ps).getResultList();
        Page<StaffDto> page = new PageImpl<>(list, p, count).map(Staff::toDto);
        return page;
    }

    public Long countStaff(StaffQueryForm form) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from Staff s where 1 = 1");
        if (StringUtils.isNotEmpty(form.getFullname())) {
            sb.append(" and s.fullname LIKE :fullname");
        }
        if (StringUtils.isNotEmpty(form.getId())) {
            sb.append(" and s.id LIKE :id");
        }
        if (form.getAge() != null) {
            sb.append(" and s.age = :age");
        }
        if (StringUtils.isNotEmpty(form.getDeptName())) {
            sb.append(" and s.dept.name LIKE :deptName");
        }
        TypedQuery<Long> q = em.createQuery(sb.toString(), Long.class);
        if (StringUtils.isNotEmpty(form.getFullname())) {
            q.setParameter("fullname", "%" + form.getFullname() + "%");
        }
        if (StringUtils.isNotEmpty(form.getId())) {
            q.setParameter("id", "%" + form.getId() + "%");
        }
        if (form.getAge() != null) {
            q.setParameter("age", form.getAge());
        }
        if (StringUtils.isNotEmpty(form.getDeptName())) {
            q.setParameter("deptName", "%" + form.getDeptName() + "%");
        }
        return q.getSingleResult();
    }

}
