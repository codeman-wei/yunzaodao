package com.yzd.modules.study.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yzd.modules.system.domain.Dept;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
* @author wdc
* @date 2020-05-03
*/
@Entity
@Getter
@Setter
@Table(name="student")
@NoArgsConstructor
public class Student implements Serializable {
    public Student(Long id) {
        this.id = id;
    }

    /** 学生id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 姓名 */
    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    /** 学号 */
    @Column(name = "student_number",nullable = false)
    @NotBlank
    private String studentNumber;

    /** 创建日期 */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    /** 学院 */
    @OneToOne
    @JoinColumn(name = "college_id")
    private Dept college;

    /** 邮箱 */
    @Column(name = "email",nullable = false)
    @NotBlank
    private String email;

    /** 状态：1启用、0禁用 */
    @Column(name = "enabled")
    private Boolean enabled;

    /** 手机号码 */
    @Column(name = "phone",nullable = false)
    @NotBlank
    private String phone;

    /** 最后修改密码的日期 */
    @Column(name = "last_password_reset_time")
    private Timestamp lastPasswordResetTime;

    /** 性别 */
    @Column(name = "sex")
    private String sex="男";

    public void copy(Student source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}