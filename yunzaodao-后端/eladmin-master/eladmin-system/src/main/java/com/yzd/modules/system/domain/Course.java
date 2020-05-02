package com.yzd.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.web.servlet.DispatcherServlet;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;


@Entity
@Data
@Table(name="course")
public class Course implements Serializable {
    /** 课程id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 课程名 */
    @Column(name = "course_name",nullable = false)
    @NotBlank
    private String courseName;

    /** 课程编码 */
    @Column(name = "course_code",nullable = false)
    private String courseCode;


    @NotNull
    private Boolean enabled;

    @NotNull
    @Column(name = "join_permission")
    private Boolean joinPermission;

    @Column
    private String semester;


    /** 选课人数 */
    @Column(name = "student_count")
    private Integer studentCount;

    /** 授课教师姓名 */
    @Column(name = "teacher_name")
    private String teacherName;

    /** 归属学院 */
    @OneToOne
    @JoinColumn(name = "college_id")
    private Dept college;

    /** 课程创建者uid */
    @OneToOne
    @JoinColumn(name = "create_uid")
    private User createUser;

    /** 签到发起次数 */
    @Column(name = "sign_count")
    private Integer signCount;


    public void copy(Course source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}