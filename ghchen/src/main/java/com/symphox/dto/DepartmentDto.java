package com.symphox.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.symphox.entity.Department;

@JsonInclude(Include.NON_NULL)
public class DepartmentDto implements Serializable {

    private static final long serialVersionUID = -7391254182836384159L;

    private String id;

    private String fullname;

    public static DepartmentDto of(Department e) {
        DepartmentDto d = new DepartmentDto();
        d.id = e.getId();
        d.fullname = e.getFullname();
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

}
