package com.yzd.modules.system.service.mapper;

import com.yzd.modules.system.domain.Dept;
import com.yzd.modules.system.service.dto.DeptSmallDto;
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
public class DeptSmallMapperImpl implements DeptSmallMapper {

    @Override
    public Dept toEntity(DeptSmallDto dto) {
        if ( dto == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( dto.getId() );
        dept.setName( dto.getName() );
        dept.setPid( dto.getPid() );

        return dept;
    }

    @Override
    public DeptSmallDto toDto(Dept entity) {
        if ( entity == null ) {
            return null;
        }

        DeptSmallDto deptSmallDto = new DeptSmallDto();

        deptSmallDto.setId( entity.getId() );
        deptSmallDto.setPid( entity.getPid() );
        deptSmallDto.setName( entity.getName() );

        return deptSmallDto;
    }

    @Override
    public List<Dept> toEntity(List<DeptSmallDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dept> list = new ArrayList<Dept>( dtoList.size() );
        for ( DeptSmallDto deptSmallDto : dtoList ) {
            list.add( toEntity( deptSmallDto ) );
        }

        return list;
    }

    @Override
    public List<DeptSmallDto> toDto(List<Dept> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeptSmallDto> list = new ArrayList<DeptSmallDto>( entityList.size() );
        for ( Dept dept : entityList ) {
            list.add( toDto( dept ) );
        }

        return list;
    }
}
