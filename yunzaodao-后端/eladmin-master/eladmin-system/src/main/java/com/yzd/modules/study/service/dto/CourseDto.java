package com.yzd.modules.study.service.dto;

import com.alibaba.druid.pool.DruidDataSource;
import com.yzd.modules.system.service.dto.DeptSmallDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
* @author wdc
* @date 2020-03-20
*/
@Data
public class CourseDto implements Serializable {

    /** 课程id */
    private Long id;

    /** 课程名 */
    private String courseName;

    /** 课程编码 */
    private String courseCode;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean joinPermission;

    private String semester;

    /** 选课人数 */
    private Integer studentCount;

    /** 授课教师姓名 */
    private String teacherName;

    /** 归属学院 */
    private DeptSmallDto college;

    /** 课程创建者名字 */
    private String userName;

    /** 签到发起次数 */
    private Integer signCount;

    private List<SignHistorySmallDto> signHistory;


}