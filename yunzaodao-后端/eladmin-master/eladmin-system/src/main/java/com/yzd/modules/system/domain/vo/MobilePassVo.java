package com.yzd.modules.system.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MobilePassVo {
    private String role;
    private String newPassword;
    private String oldPassword;
    private String count;
}
