package com.yzd.modules.study.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SignHistorySmallDto {
    /** id */
    private Long id;

    /** 出勤人数 */
    private Integer attendance;

    /** 缺勤人数 */
    private Integer absence;

    /** 发起时间 */
    private Timestamp createTime;
}
