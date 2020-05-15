package com.yzd.modules.study.service.dto;

import com.yzd.modules.study.domain.SysVal;
import lombok.Data;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
* @author wdc
* @date 2020-05-13
*/
@Data
public class UserSysValDto implements Serializable {

    /**
     * 防止精度丢失
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long userId;

    private SysValDto sysVal;

    private Integer value;
}