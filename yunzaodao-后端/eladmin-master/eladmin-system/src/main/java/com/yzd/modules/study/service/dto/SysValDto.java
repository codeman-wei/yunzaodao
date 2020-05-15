package com.yzd.modules.study.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Setter
@Getter
public class SysValDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;
    private String remark;
    private Integer defaultValue;
}
