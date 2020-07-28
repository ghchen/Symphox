package com.symphox.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.symphox.dto.DepartmentDto;
import com.symphox.entity.Department;
import com.symphox.error.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService ds;

    @Test
    public void test01Create() throws Exception {
        DepartmentDto form = new DepartmentDto();
        form.setId("root");
        form.setFullname("董事長室");
        ds.create(form);

        Department dept = ds.findById("root").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(dept);
        Assert.assertEquals(dept.getFullname(), "董事長室");
        Assert.assertEquals(dept.isEnabled(), true);
    }

    @Test
    public void test02Update() throws Exception {
        DepartmentDto d = ds.fetch("root");
        d.setFullname("董事長辦公室");
        ds.update(d);

        Department dept = ds.findById("root").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(dept);
        Assert.assertEquals(dept.getFullname(), "董事長辦公室");
    }

    @Test
    public void test03Disable() throws Exception {
        ds.disable("root");

        Department dept = ds.findById("root").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(dept);
        Assert.assertEquals(dept.isEnabled(), false);
    }

    @Test
    public void test04Enabled() throws Exception {
        ds.enabled("root");

        Department dept = ds.findById("root").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(dept);
        Assert.assertEquals(dept.isEnabled(), true);
    }

}
