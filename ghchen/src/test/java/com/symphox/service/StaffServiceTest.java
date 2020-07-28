package com.symphox.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import com.symphox.dto.DepartmentDto;
import com.symphox.dto.StaffDto;
import com.symphox.dto.StaffQueryForm;
import com.symphox.entity.Department;
import com.symphox.entity.Staff;
import com.symphox.entity.Staff.Gender;
import com.symphox.error.ResourceNotFoundException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffServiceTest {

    @Autowired
    private DepartmentService ds;

    @Autowired
    private StaffService ss;

    @Test
    public void test01Create() throws Exception {
        // Mockito.when(ds.findById("officeT")).thenReturn(getDept("officeT"));
        // mockito error when JPA save.
        getOrCreateDept("officeT");
        for (int i = 1; i <= 30; i++) {
            StaffDto s = new StaffDto();
            s.setId("E000" + i);
            s.setFullname("Staff " + i);
            s.setDeptId("officeT");
            s.setGender(i % 2 == 0 ? Gender.Female : Gender.Male);
            s.setPhone(s.hashCode() + "");
            s.setAddress(DigestUtils.md5DigestAsHex(s.toString().getBytes()));
            s.setAge(20 + i);
            ss.create(s);
        }

        Staff staff = ss.findById("E0001").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(staff);
        Assert.assertEquals(staff.getFullname(), "Staff 1");
        Assert.assertEquals(staff.getAge(), 21);
    }

    @Test
    public void test02Update() throws Exception {
        StaffDto d = ss.fetch("E0001");
        d.setGender(Gender.Female);
        ss.update(d);

        Staff staff = ss.findById("E0001").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(staff);
        Assert.assertEquals(staff.getGender(), Gender.Female);
    }

    @Test
    public void test03Delete() throws Exception {
        ss.disable("E0001");

        Staff staff = ss.findById("E0001").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(staff);
        Assert.assertEquals(staff.isEnabled(), false);
    }

    @Test
    public void test04Enabled() throws Exception {
        ss.enabled("E0001");

        Staff staff = ss.findById("E0001").orElseThrow(ResourceNotFoundException::new);
        Assert.assertNotNull(staff);
        Assert.assertEquals(staff.isEnabled(), true);
    }

    @Test
    public void test05Count() throws Exception {
        StaffQueryForm form = new StaffQueryForm();
        form.setDeptName("officeT");

        long count = ss.countStaff(form);
        Assert.assertEquals(count, 30);
    }

    @Test
    public void test06Query() throws Exception {
        StaffQueryForm form = new StaffQueryForm();
        form.setAge(25);
        form.setDeptName("officeT");

        Page<StaffDto> page = ss.query(form, PageRequest.of(0, 10));
        Assert.assertNotNull(page);
        Assert.assertFalse(page.getContent().isEmpty());
        Assert.assertNotNull(page.getContent().get(0));
        Assert.assertEquals(page.getContent().get(0).getFullname(), "Staff 5");
        Assert.assertEquals(page.getContent().get(0).getGender(), Gender.Male);
    }

    Optional<Department> getDept(String id) {
        Department dept = new Department();
        dept.setId(id);
        dept.setEnabled(true);
        dept.setFullname("Name of " + id);
        return Optional.of(dept);
    }

    Optional<Department> getOrCreateDept(String id) {
        Optional<Department> opt = ds.findById(id);
        if (opt.isPresent()) {
            return opt;
        }
        else {
            DepartmentDto form = new DepartmentDto();
            form.setId(id);
            form.setFullname("Name of " + id);
            ds.create(form);
            return ds.findById(id);
        }
    }

}
