package com.yzd.modules.study.service.dto;

import com.yzd.annotation.Query;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
* @author wdc
* @date 2020-05-03
*/
@Getter
@Setter
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