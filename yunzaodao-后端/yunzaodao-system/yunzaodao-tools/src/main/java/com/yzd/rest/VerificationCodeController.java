package com.yzd.rest;

import com.yzd.annotation.AnonymousAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.yzd.domain.VerificationCode;
import com.yzd.domain.vo.EmailVo;
import com.yzd.service.EmailService;
import com.yzd.service.VerificationCodeService;
import com.yzd.utils.ElAdminConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/code")
@Api(tags = "工具：验证码管理")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;

    public VerificationCodeController(VerificationCodeService verificationCodeService,  EmailService emailService) {
        this.verificationCodeService = verificationCodeService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public ResponseEntity<Object> resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(ElAdminConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping(value = "/email/resetPass")
//    @ApiOperation("重置密码，发送验证码")
//    @AnonymousAccess
//    public ResponseEntity<Object> resetPass(@RequestBody VerificationCode code) throws Exception{
//        // 验证用户是否存在
////        userService.checkExist(code.getValue());
//        code.setScenes(ElAdminConstant.RESET_PASS);
//        EmailVo emailVo = verificationCodeService.sendEmail(code);
//        emailVo.setSubject("云早到后台管理系统重置密码验证");
//        emailService.send(emailVo);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping(value = "/validated")
    @ApiOperation("验证码验证")
    @AnonymousAccess
    public ResponseEntity<Object> validated(@RequestBody VerificationCode code){
        verificationCodeService.validated(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    public void validated(){
        System.out.println(emailService.find());
    }
}
