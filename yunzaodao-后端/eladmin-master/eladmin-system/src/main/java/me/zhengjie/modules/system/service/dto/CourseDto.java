package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author wdc
* @date 2020-03-20
*/
@Data
public class CourseDto implements Serializable {

    /** 课程id */
    private Integer id;

    /** 课程名 */
    private String courseName;

    /** 课程编码 */
    private String courseCode;

    /** 上课地点，星期与时间用；间隔 */
    private String coursePlace;

    /** 上课时间 */
    private String courseTime;

    /** 选课人数 */
    private Integer studentCount;

    /** 授课教师姓名 */
    private String teacherName;

    /** 归属学院 */
    private String belongCollege;

    /** 课程创建者uid */
    private Integer createUid;

    /** 签到发起次数 */
    private Integer signCount;

    /** 课程开始时间 */
    private String startTime;

    /** 课程截止时间 */
    private String endTime;
}