package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @author wdc
* @date 2020-03-20
*/
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
    @NotBlank
    private String courseCode;

    /** 上课地点，星期与时间用；间隔 */
    @Column(name = "course_place")
    private String coursePlace;

    /** 上课时间 */
    @Column(name = "course_time")
    private String courseTime;

    /** 选课人数 */
    @Column(name = "student_count")
    private Integer studentCount;

    /** 授课教师姓名 */
    @Column(name = "teacher_name")
    private String teacherName;

    /** 归属学院 */
    @Column(name = "belong_college")
    private String belongCollege;

    /** 课程创建者uid */
    @Column(name = "create_uid")
    private Integer createUid;

    /** 签到发起次数 */
    @Column(name = "sign_count")
    private Integer signCount;

    /** 课程开始时间 */
    @Column(name = "start_time")
    private String startTime;

    /** 课程截止时间 */
    @Column(name = "end_time")
    private String endTime;

    public void copy(Course source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}