package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.Course;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.modules.system.domain.Dept;
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
public class SignHistoryMapperImpl implements SignHistoryMapper {

    @Override
    public SignHistory toEntity(SignHistoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        SignHistory signHistory = new SignHistory();

        signHistory.setId( dto.getId() );
        signHistory.setAttendance( dto.getAttendance() );
        signHistory.setAbsence( dto.getAbsence() );
        signHistory.setCreateTime( dto.getCreateTime() );

        return signHistory;
    }

    @Override
    public List<SignHistory> toEntity(List<SignHistoryDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SignHistory> list = new ArrayList<SignHistory>( dtoList.size() );
        for ( SignHistoryDto signHistoryDto : dtoList ) {
            list.add( toEntity( signHistoryDto ) );
        }

        return list;
    }

    @Override
    public List<SignHistoryDto> toDto(List<SignHistory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SignHistoryDto> list = new ArrayList<SignHistoryDto>( entityList.size() );
        for ( SignHistory signHistory : entityList ) {
            list.add( toDto( signHistory ) );
        }

        return list;
    }

    @Override
    public SignHistoryDto toDto(SignHistory entity) {
        if ( entity == null ) {
            return null;
        }

        SignHistoryDto signHistoryDto = new SignHistoryDto();

        String name = entityCourseCollegeName( entity );
        if ( name != null ) {
            signHistoryDto.setCollegeName( name );
        }
        String courseName = entityCourseCourseName( entity );
        if ( courseName != null ) {
            signHistoryDto.setCourseName( courseName );
        }
        String teacherName = entityCourseTeacherName( entity );
        if ( teacherName != null ) {
            signHistoryDto.setTeacherName( teacherName );
        }
        String courseCode = entityCourseCourseCode( entity );
        if ( courseCode != null ) {
            signHistoryDto.setCourseCode( courseCode );
        }
        signHistoryDto.setId( entity.getId() );
        signHistoryDto.setAttendance( entity.getAttendance() );
        signHistoryDto.setAbsence( entity.getAbsence() );
        signHistoryDto.setCreateTime( entity.getCreateTime() );

        return signHistoryDto;
    }

    private String entityCourseCollegeName(SignHistory signHistory) {
        if ( signHistory == null ) {
            return null;
        }
        Course course = signHistory.getCourse();
        if ( course == null ) {
            return null;
        }
        Dept college = course.getCollege();
        if ( college == null ) {
            return null;
        }
        String name = college.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityCourseCourseName(SignHistory signHistory) {
        if ( signHistory == null ) {
            return null;
        }
        Course course = signHistory.getCourse();
        if ( course == null ) {
            return null;
        }
        String courseName = course.getCourseName();
        if ( courseName == null ) {
            return null;
        }
        return courseName;
    }

    private String entityCourseTeacherName(SignHistory signHistory) {
        if ( signHistory == null ) {
            return null;
        }
        Course course = signHistory.getCourse();
        if ( course == null ) {
            return null;
        }
        String teacherName = course.getTeacherName();
        if ( teacherName == null ) {
            return null;
        }
        return teacherName;
    }

    private String entityCourseCourseCode(SignHistory signHistory) {
        if ( signHistory == null ) {
            return null;
        }
        Course course = signHistory.getCourse();
        if ( course == null ) {
            return null;
        }
        String courseCode = course.getCourseCode();
        if ( courseCode == null ) {
            return null;
        }
        return courseCode;
    }
}
