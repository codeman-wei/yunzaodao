package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.SysVal;
import com.yzd.modules.study.domain.UserSysVal;
import com.yzd.modules.study.service.dto.SysValDto;
import com.yzd.modules.study.service.dto.UserSysValDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-30T21:27:54+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
@Component
public class UserSysValMapperImpl implements UserSysValMapper {

    @Override
    public UserSysVal toEntity(UserSysValDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserSysVal userSysVal = new UserSysVal();

        userSysVal.setId( dto.getId() );
        userSysVal.setUserId( dto.getUserId() );
        userSysVal.setSysVal( sysValDtoToSysVal( dto.getSysVal() ) );
        userSysVal.setValue( dto.getValue() );

        return userSysVal;
    }

    @Override
    public UserSysValDto toDto(UserSysVal entity) {
        if ( entity == null ) {
            return null;
        }

        UserSysValDto userSysValDto = new UserSysValDto();

        userSysValDto.setId( entity.getId() );
        userSysValDto.setUserId( entity.getUserId() );
        userSysValDto.setSysVal( sysValToSysValDto( entity.getSysVal() ) );
        userSysValDto.setValue( entity.getValue() );

        return userSysValDto;
    }

    @Override
    public List<UserSysVal> toEntity(List<UserSysValDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserSysVal> list = new ArrayList<UserSysVal>( dtoList.size() );
        for ( UserSysValDto userSysValDto : dtoList ) {
            list.add( toEntity( userSysValDto ) );
        }

        return list;
    }

    @Override
    public List<UserSysValDto> toDto(List<UserSysVal> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserSysValDto> list = new ArrayList<UserSysValDto>( entityList.size() );
        for ( UserSysVal userSysVal : entityList ) {
            list.add( toDto( userSysVal ) );
        }

        return list;
    }

    protected SysVal sysValDtoToSysVal(SysValDto sysValDto) {
        if ( sysValDto == null ) {
            return null;
        }

        SysVal sysVal = new SysVal();

        sysVal.setId( sysValDto.getId() );
        sysVal.setName( sysValDto.getName() );
        sysVal.setRemark( sysValDto.getRemark() );
        sysVal.setDefaultValue( sysValDto.getDefaultValue() );

        return sysVal;
    }

    protected SysValDto sysValToSysValDto(SysVal sysVal) {
        if ( sysVal == null ) {
            return null;
        }

        SysValDto sysValDto = new SysValDto();

        sysValDto.setId( sysVal.getId() );
        sysValDto.setName( sysVal.getName() );
        sysValDto.setRemark( sysVal.getRemark() );
        sysValDto.setDefaultValue( sysVal.getDefaultValue() );

        return sysValDto;
    }
}
