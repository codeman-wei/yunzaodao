package com.yzd.modules.study.domain;

import com.yzd.modules.system.domain.Dept;
import com.yzd.modules.system.domain.DictDetail;
import com.yzd.modules.system.domain.User;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name="course")
public class Course implements Serializable {
    /** 课程id */
    @Id

    @Column(name = "id")
    private Long id;

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

    @ManyToMany
    @JoinTable(name = "course_student", joinColumns = {@JoinColumn(name = "course_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "id")})
    private Set<Student> students;

    @OneToMany(mappedBy = "course",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<SignHistory> signHistory;

    public void copy(Course source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", joinPermission=" + joinPermission +
                ", semester='" + semester + '\'' +
                ", studentCount=" + studentCount +
                ", teacherName='" + teacherName + '\'' +
                ", college=" + college.getName() +
                ", createUser=" + createUser +
                ", signCount=" + signCount +
                ", signHistory=" + signHistory +
                '}';
    }
}