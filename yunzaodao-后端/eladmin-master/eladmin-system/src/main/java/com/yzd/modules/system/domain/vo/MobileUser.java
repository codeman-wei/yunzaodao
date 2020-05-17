package com.yzd.modules.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MobileUser {
    private Long id;
    private String phone;
    private String name;
    private String sex;
    private String status;
    private String school;
    private String college;
    private String number;
    private String email;
}
