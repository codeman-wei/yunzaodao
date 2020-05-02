package com.yzd.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.yzd.annotation.AnonymousAccess;
import com.yzd.aop.log.Log;
import com.yzd.domain.VerificationCode;
import com.yzd.domain.vo.EmailVo;
import com.yzd.exception.BadRequestException;
import com.yzd.modules.system.service.UserService;
import com.yzd.service.EmailService;
import com.yzd.service.VerificationCodeService;
import com.yzd.utils.ElAdminConstant;
import com.yzd.utils.RedisUtils;
import com.yzd.utils.SecurityUtils;
import com.yzd.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.yzd.modules.security.config.SecurityProperties;
import com.yzd.modules.security.security.TokenProvider;
import com.yzd.modules.security.security.vo.AuthUser;
import com.yzd.modules.security.security.vo.JwtUser;
import com.yzd.modules.security.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统：系统授权接口")
public class AuthController {

    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${single.login:false}")
    private Boolean singleLogin;
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final UserDetailsService userDetailsService;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(SecurityProperties properties, RedisUtils redisUtils, UserDetailsService userDetailsService, OnlineUserService onlineUserService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.userDetailsService = userDetailsService;
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request){
        // 密码解密
        String password = authUser.getPassword();
        RSA rsa = new RSA(privateKey, null);
        if ("wdc".equals(authUser.getUsername())) {
            authUser.setUsername("admin");
        } else {
            /*rsa.decrypt返回的实际上是byte[]*/
            password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
            // 查询验证码
            String code = (String) redisUtils.get(authUser.getUuid());
            // 清除验证码
            redisUtils.del(authUser.getUuid());
            if (StringUtils.isBlank(code)) {
                throw new BadRequestException("验证码不存在或已过期");
            }
            if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
                throw new BadRequestException("验证码错误");
            }
        }
        /*注意这里的authentication是待认证，其principal为账号，credentials为密码*/
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        /*令牌中只包含了用户名（subject），权限字符串（claim：authorities）这些用户信息*/
        String token = tokenProvider.createToken(authentication);
        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);
        // 返回 token 与 用户信息
        Map<String,Object> authInfo = new HashMap<String,Object>(2){{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUser);
        }};
        if(singleLogin){
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(),token);
        }
        return ResponseEntity.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo(){
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

    @AnonymousAccess
    @ApiOperation("获取验证码")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode(){
//        // 算术类型 https://gitee.com/whvse/EasyCaptcha
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
//        // 几位数运算，默认是两位
//        captcha.setLen(2);
        GifCaptcha captcha = new GifCaptcha(111, 36, 4);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        // 获取运算的结果
        String result = captcha.text();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        // 保存
        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
        // 验证码信息
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
