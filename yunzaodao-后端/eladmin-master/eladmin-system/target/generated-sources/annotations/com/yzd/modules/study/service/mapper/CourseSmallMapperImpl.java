package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.Course;
import com.yzd.modules.study.service.dto.CouserSmallDto;
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
public class CourseSmallMapperImpl implements CourseSmallMapper {

    @Override
    public Course toEntity(CouserSmallDto dto) {
        if ( dto == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( dto.getId() );
        course.setClassName( dto.getClassName() );
        course.setCourseName( dto.getCourseName() );
        course.setCourseCode( dto.getCourseCode() );
        course.setSemester( dto.getSemester() );
        course.setTeacherName( dto.getTeacherName() );
        course.setCollege( deptSmallDtoToDept( dto.getCollege() ) );

        return course;
    }

    @Override
    public CouserSmallDto toDto(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CouserSmallDto couserSmallDto = new CouserSmallDto();

        couserSmallDto.setId( entity.getId() );
        couserSmallDto.setClassName( entity.getClassName() );
        couserSmallDto.setCourseName( entity.getCourseName() );
        couserSmallDto.setTeacherName( entity.getTeacherName() );
        couserSmallDto.setCourseCode( entity.getCourseCode() );
        couserSmallDto.setSemester( entity.getSemester() );
        couserSmallDto.setCollege( deptToDeptSmallDto( entity.getCollege() ) );

        return couserSmallDto;
    }

    @Override
    public List<Course> toEntity(List<CouserSmallDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( dtoList.size() );
        for ( CouserSmallDto couserSmallDto : dtoList ) {
            list.add( toEntity( couserSmallDto ) );
        }

        return list;
    }

    @Override
    public List<CouserSmallDto> toDto(List<Course> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CouserSmallDto> list = new ArrayList<CouserSmallDto>( entityList.size() );
        for ( Course course : entityList ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    protected Dept deptSmallDtoToDept(DeptSmallDto deptSmallDto) {
        if ( deptSmallDto == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( deptSmallDto.getId() );
        dept.setName( deptSmallDto.getName() );
        dept.setPid( deptSmallDto.getPid() );

        return dept;
    }

    protected DeptSmallDto deptToDeptSmallDto(Dept dept) {
        if ( dept == null ) {
            return null;
        }

        DeptSmallDto deptSmallDto = new DeptSmallDto();

        deptSmallDto.setId( dept.getId() );
        deptSmallDto.setPid( dept.getPid() );
        deptSmallDto.setName( dept.getName() );

        return deptSmallDto;
    }
}
