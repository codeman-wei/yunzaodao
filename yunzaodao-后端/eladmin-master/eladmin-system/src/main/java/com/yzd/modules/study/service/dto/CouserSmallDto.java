package com.yzd.modules.study.service.dto;

import com.yzd.modules.system.service.dto.DeptSmallDto;
import lombok.Data;

@Data
public class CouserSmallDto {
    /** 课程id */
    private Long id;

    /** 班级 **/
    private String className;

    /** 课程名 */
    private String courseName;

    private String teacherName;

    /** 课程编码 */
    private String courseCode;

    private String semester;

    /** 归属学院 */
    private DeptSmallDto college;
}
