package com.yzd.modules.security.service;

import com.yzd.exception.BadRequestException;
import com.yzd.modules.system.service.RoleService;
import com.yzd.modules.system.service.UserService;
import com.yzd.modules.system.service.dto.DeptSmallDto;
import com.yzd.modules.system.service.dto.MajorSmallDto;
import com.yzd.modules.system.service.dto.UserDto;
import com.yzd.modules.security.security.vo.JwtUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * UserDetailsService向核心组件们提供数据层的用户信息，而用户信息在这里被封装成了UserDetail
 * UserDetailsService是DaoAuthenticationProvider的一个组件
 * 返回的数据类型是UserDetails的扩展类JWTService，封装了用户名、密码和授权信息等信息
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final RoleService roleService;

    public UserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserDto user = userService.findByName(username);
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(UserDto user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getNickName(),
                user.getSex(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhone(),
                Optional.ofNullable(user.getDept()).map(DeptSmallDto::getName).orElse(null),
                Optional.ofNullable(user.getJob()).map(MajorSmallDto::getName).orElse(null),
                roleService.mapToGrantedAuthorities(user),
                user.getEnabled(),
                user.getCreateTime(),
                user.getLastPasswordResetTime()
        );
    }
}
