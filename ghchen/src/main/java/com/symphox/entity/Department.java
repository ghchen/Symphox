package com.symphox.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.symphox.dto.DepartmentDto;

@Entity
@Table(name = "Department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1007641781560221906L;

    /**
     * 部門編號
     */
    @Id
    private String id;

    /**
     * 部門名稱
     */
    @Column(nullable = false, length = 120)
    private String fullname;

    /**
     * 資料不刪除，僅標記為`不啟用`
     */
    private boolean enabled;

    public DepartmentDto toDto() {
        return DepartmentDto.of(this);
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
