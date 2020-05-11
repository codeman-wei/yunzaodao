package com.yzd.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;


@Data
public class DeptSmallDto implements Serializable {

    private Long id;

    private Long pid;

    private String name;
}