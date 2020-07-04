package com.yzd.modules.study.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import com.yzd.annotation.Query;

/**
* @author wdc
* @date 2020-05-05
*/
@Data
public class SignHistoryQueryCriteria{
    @Query(type = Query.Type.INNER_LIKE, propName="courseName", joinName = "course")
    private String courseName;

    @Query(type = Query.Type.INNER_LIKE, propName="courseCode", joinName = "course")
    private String courseCode;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}