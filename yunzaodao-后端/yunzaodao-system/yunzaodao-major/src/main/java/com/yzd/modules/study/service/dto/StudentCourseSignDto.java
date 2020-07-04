package com.yzd.modules.study.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author wdc
* @date 2020-05-05
*/
@Getter
@Setter
public class StudentCourseSignDto implements Serializable {

    private Long id;

    /** 签到历史id */
    private SignHistoryDto signHistory;

    /** 学生 */
    private StudentSmallDto student;

    /** 是否出勤 */
    private Boolean attendance;

    /** 是否补签 */
    private Boolean replenish;

    /** 签到时间 */
    private Timestamp createTime;
}