package com.yzd.modules.study.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yzd.modules.system.service.dto.DeptSmallDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author wdc
* @date 2020-05-03
*/
@Getter
@Setter
public class StudentDto implements Serializable {

    /** 学生id */
    private Long id;

    /** 姓名 */
    private String name;

    /** 学号 */
    private String studentNumber;

    @JsonIgnore
    private String password;

    /** 创建日期 */
    private Timestamp createTime;

    /** 学院 */
    private DeptSmallDto college;

    /** 邮箱 */
    private String email;

    /** 状态：1启用、0禁用 */
    private Boolean enabled;

    /** 手机号码 */
    private String phone;

    /** 最后修改密码的日期 */
    private Timestamp lastPasswordResetTime;

    /** 性别 */
    private String sex;

    /** 经验值*/
    private Integer experience;
}