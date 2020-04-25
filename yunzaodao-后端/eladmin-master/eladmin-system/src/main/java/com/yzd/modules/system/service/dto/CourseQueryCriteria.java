package com.yzd.modules.system.service.dto;

import lombok.Data;
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