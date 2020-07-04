package com.yzd.modules.system.service.mapper;

import com.yzd.modules.system.domain.Dept;
import com.yzd.modules.system.domain.Menu;
import com.yzd.modules.system.domain.Role;
import com.yzd.modules.system.service.dto.DeptDto;
import com.yzd.modules.system.service.dto.MenuDto;
import com.yzd.modules.system.service.dto.RoleDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-30T21:27:54+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Role toEntity(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( dto.getId() );
        role.setName( dto.getName() );
        role.setDataScope( dto.getDataScope() );
        role.setLevel( dto.getLevel() );
        role.setRemark( dto.getRemark() );
        role.setPermission( dto.getPermission() );
        role.setMenus( menuDtoSetToMenuSet( dto.getMenus() ) );
        role.setDepts( deptDtoSetToDeptSet( dto.getDepts() ) );
        role.setCreateTime( dto.getCreateTime() );

        return role;
    }

    @Override
    public RoleDto toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( entity.getId() );
        roleDto.setName( entity.getName() );
        roleDto.setDataScope( entity.getDataScope() );
        roleDto.setLevel( entity.getLevel() );
        roleDto.setRemark( entity.getRemark() );
        roleDto.setPermission( entity.getPermission() );
        roleDto.setMenus( menuSetToMenuDtoSet( entity.getMenus() ) );
        roleDto.setDepts( deptSetToDeptDtoSet( entity.getDepts() ) );
        roleDto.setCreateTime( entity.getCreateTime() );

        return roleDto;
    }

    @Override
    public List<Role> toEntity(List<RoleDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleDto roleDto : dtoList ) {
            list.add( toEntity( roleDto ) );
        }

        return list;
    }

    @Override
    public List<RoleDto> toDto(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleDto> list = new ArrayList<RoleDto>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDto( role ) );
        }

        return list;
    }

    protected Set<Menu> menuDtoSetToMenuSet(Set<MenuDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Menu> set1 = new HashSet<Menu>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( MenuDto menuDto : set ) {
            set1.add( menuMapper.toEntity( menuDto ) );
        }

        return set1;
    }

    protected Set<Dept> deptDtoSetToDeptSet(Set<DeptDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Dept> set1 = new HashSet<Dept>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DeptDto deptDto : set ) {
            set1.add( deptMapper.toEntity( deptDto ) );
        }

        return set1;
    }

    protected Set<MenuDto> menuSetToMenuDtoSet(Set<Menu> set) {
        if ( set == null ) {
            return null;
        }

        Set<MenuDto> set1 = new HashSet<MenuDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Menu menu : set ) {
            set1.add( menuMapper.toDto( menu ) );
        }

        return set1;
    }

    protected Set<DeptDto> deptSetToDeptDtoSet(Set<Dept> set) {
        if ( set == null ) {
            return null;
        }

        Set<DeptDto> set1 = new HashSet<DeptDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Dept dept : set ) {
            set1.add( deptMapper.toDto( dept ) );
        }

        return set1;
    }
}
