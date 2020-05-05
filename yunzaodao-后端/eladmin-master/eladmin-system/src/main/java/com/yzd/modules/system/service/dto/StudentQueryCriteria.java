package com.yzd.modules.system.service.dto;

import com.yzd.annotation.Query;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
* @author wdc
* @date 2020-05-03
*/
@Data
public class StudentQueryCriteria implements Serializable {


    @Query(propName = "id", type = Query.Type.IN, joinName = "college")
    private Set<Long> collegeIds;

    @Query(blurry = "email,name,studentNumber")
    private String blurry;

    @Query
    private Boolean enabled;

    private Long collegeId;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}