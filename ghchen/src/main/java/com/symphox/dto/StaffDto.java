package com.symphox.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.symphox.entity.Staff;
import com.symphox.entity.Staff.Gender;

@JsonInclude(Include.NON_NULL)
public class StaffDto implements Serializable {

    private static final long serialVersionUID = -7391254182836384159L;

    private String id;

    private String fullname;

    private DepartmentDto dept;

    private String deptId;

    private String deptName;

    private Gender gender;

    private String phone;

    private String address;

    private int age;

    public static StaffDto of(Staff e) {
        StaffDto d = new StaffDto();
        d.id = e.getId();
        d.fullname = e.getFullname();
        if (e.getDept() != null) {
            d.dept = e.getDept().toDto();
            d.deptId = e.getDept().getId();
        }
        d.gender = e.getGender();
        d.phone = e.getPhone();
        d.address = e.getAddress();
        d.age = e.getAge();
        return d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public DepartmentDto getDept() {
        return dept;
    }

    public void setDept(DepartmentDto dept) {
        this.dept = dept;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
