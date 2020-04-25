package com.yzd.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class MajorSmallDto implements Serializable {

    private Long id;

    private String name;
}