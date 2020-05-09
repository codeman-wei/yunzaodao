package com.yzd.modules.study.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import com.yzd.annotation.Query;

/**
* @author wdc
* @date 2020-03-20
*/
@Data
public class CourseQueryCriteria{
    @Query(blurry = "courseName,courseCode")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
//    /** 模糊 */
//    @Query(type = Query.Type.INNER_LIKE)
//    private String courseName;
//
//    /** 模糊 */
//    @Query(type = Query.Type.INNER_LIKE)
//    private String courseCode;

//    /** 精确 */
//    @Query
//    private String belongCollege;
//    @Query(type = Query.Type.BETWEEN)
//    private List<String> createTime;
}