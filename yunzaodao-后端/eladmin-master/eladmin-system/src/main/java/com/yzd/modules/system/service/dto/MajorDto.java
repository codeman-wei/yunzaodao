package com.yzd.modules.system.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
public class MajorDto implements Serializable {

    private Long id;

    private Long sort;

    private String name;

    private Boolean enabled;

    private DeptDto dept;

    private String deptSuperiorName;

    private Timestamp createTime;

    public MajorDto(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}