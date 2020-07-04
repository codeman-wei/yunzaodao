package com.yzd.modules.study.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StudentSmallDto implements Serializable {
    private Long id;
    /** 姓名 */
    private String name;
    /** 学号 */
    private String studentNumber;
}
