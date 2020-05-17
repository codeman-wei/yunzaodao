package com.yzd.modules.system.rest;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.yzd.annotation.AnonymousAccess;
import com.yzd.aop.log.Log;
import com.yzd.exception.BadRequestException;
import com.yzd.modules.security.security.vo.AuthUser;
import com.yzd.modules.security.security.vo.JwtUser;
import com.yzd.modules.security.security.vo.MobileAuth;
import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.domain.SysVal;
import com.yzd.modules.study.service.StudentService;
import com.yzd.modules.study.service.dto.StudentDto;
import com.yzd.modules.system.domain.User;
import com.yzd.modules.system.domain.vo.MobilePassVo;
import com.yzd.modules.system.domain.vo.MobileUser;
import com.yzd.modules.system.service.UserService;
import com.yzd.modules.system.service.dto.UserDto;
import com.yzd.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/mobile")
public class MobileController {

    private final StudentService studentService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public MobileController(StudentService studentService, UserService userService, PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/check")
    @AnonymousAccess
    public ResponseEntity<Object> checkRegister(String phone) {
        if (userService.checkRegister(phone)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register")
    @AnonymousAccess
    public ResponseEntity<Object> register(@Validated @RequestBody Student resource) {
        resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        resource.setEnabled(true);
        studentService.create(resource);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody MobileAuth authUser){
        UserDto teacher = userService.findByPhoneOrEmail(authUser.getUsername());
//        MobileUser user;
        if (teacher != null) {
            if(passwordEncoder.matches(authUser.getPassword(), teacher.getPassword())) {
//                user = new MobileUser(teacher.getPhone(), teacher.getNickName(), teacher.getSex(), "教师",
//                        "福州大学", teacher.getDept().getName(), "");
//                return new ResponseEntity<>(user, HttpStatus.OK);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("密码错误");
            }
        } else {
            StudentDto student = studentService.findByName(authUser.getUsername());
            if (student != null && passwordEncoder.matches(authUser.getPassword(), student.getPassword())) {
//                user = new MobileUser(student.getPhone(), student.getName(), student.getSex(), "学生",
//                        "福州大学", student.getCollege().getName(), student.getStudentNumber());
//                return new ResponseEntity<>(user, HttpStatus.OK);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("密码错误");
            }
        }
    }

    @AnonymousAccess
    @PostMapping(value = "/password/change")
    public ResponseEntity<Object> changePassword(@RequestBody MobilePassVo passVo){
        if("student".equals(passVo.getRole())) {
            Student user = studentService.findByCount(passVo.getCount());
            if (user != null && passwordEncoder.matches(passVo.getOldPassword(), user.getPassword())) {
                studentService.updatePass(passVo.getCount(), passwordEncoder.encode(passVo.getNewPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("旧密码错误");
            }
        }

        if("teacher".equals(passVo.getRole())) {
            UserDto teacher = userService.findByPhoneOrEmail(passVo.getCount());
            if (passwordEncoder.matches(passVo.getOldPassword(), teacher.getPassword())) {
                userService.updatePassByPhone(passVo.getCount(), passwordEncoder.encode(passVo.getNewPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("旧密码错误");
            }
        }
        // 400 错误
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "/userInfo")
    @AnonymousAccess
    public ResponseEntity<Object> getTeacher(String phone) {
        UserDto teacher = userService.findByPhoneOrEmail(phone);
        MobileUser user;
        if (teacher != null) {
            user = new MobileUser(teacher.getId(),teacher.getPhone(), teacher.getNickName(), teacher.getSex(), "教师",
                    "福州大学", teacher.getDept().getName(), "",teacher.getEmail());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        StudentDto student = studentService.findByName(phone);
        user = new MobileUser(student.getId(),student.getPhone(), student.getName(), student.getSex(), "学生",
                "福州大学", student.getCollege().getName(), student.getStudentNumber(), student.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value = "/student/update")
    @AnonymousAccess
    public ResponseEntity<Object> updateStudent(@RequestBody Student resources) {
        studentService.update(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
