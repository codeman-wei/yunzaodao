package com.yzd.service;

import com.yzd.domain.VerificationCode;
import com.yzd.domain.vo.EmailVo;


public interface VerificationCodeService {

    /**
     * 发送邮件验证码
     * @param code 验证码
     * @return EmailVo
     */
    EmailVo sendEmail(VerificationCode code);

    /**
     * 验证
     * @param code 验证码
     */
    void validated(VerificationCode code);
}
