package com.symphox.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.symphox.dto.StaffDto;

@Entity
@Table(name = "Staff")
public class Staff implements Serializable {

    private static final long serialVersionUID = -824518921489027121L;

    /**
     * 員工編號
     */
    @Id
    private String id;

    /**
     * 姓名
     */
    @Column(nullable = false, length = 120)
    private String fullname;

    /**
     * 部門ID
     */
    @ManyToOne(optional = false)
    private Department dept;

    /**
     * 性別
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * 電話
     */
    @Column(nullable = true, length = 32)
    private String phone;

    /**
     * 地址
     */
    @Column(nullable = false, length = 512)
    private String address;

    /**
     * 年齡
     */
    @Column(nullable = false)
    private int age;

    /**
     * 建立時間
     */
    @Column(nullable = false)
    private Instant createdTime;

    /**
     * 最後一次修改時間
     */
    @Column(nullable = false)
    private Instant lastUpdated;

    /**
     * 資料不刪除，僅標記為`不啟用`
     */
    private boolean enabled;

    public enum Gender {

        Male("男"),

        Female("女"),

        Others("其他"),

        NA("不提供"),

        ;

        private final String label;

        Gender(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public StaffDto toDto() {
        return StaffDto.of(this);
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

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
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

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
