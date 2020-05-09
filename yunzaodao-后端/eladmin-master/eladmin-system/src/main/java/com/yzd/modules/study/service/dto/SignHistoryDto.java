package com.yzd.modules.study.service.dto;

import com.yzd.modules.study.service.dto.CourseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author wdc
* @date 2020-05-05
*/
@Getter
@Setter
public class SignHistoryDto implements Serializable {

    /** id */
    private Long id;

    /** 课程id */
//    private CourseDto course;

    private String courseName;

    private String courseCode;

    private String collegeName;

    private String teacherName;

    /** 出勤人数 */
    private Integer attendance;

    /** 缺勤人数 */
    private Integer absence;

    /** 发起时间 */
    private Timestamp createTime;
}