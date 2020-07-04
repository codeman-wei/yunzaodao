package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.Course;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.CourseDto;
import com.yzd.modules.study.service.dto.SignHistorySmallDto;
import com.yzd.modules.system.domain.Dept;
import com.yzd.modules.system.domain.User;
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
public class CourseMapperImpl implements CourseMapper {

    @Override
    public Course toEntity(CourseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( dto.getId() );
        course.setClassName( dto.getClassName() );
        course.setCourseName( dto.getCourseName() );
        course.setCourseCode( dto.getCourseCode() );
        course.setEnabled( dto.getEnabled() );
        course.setJoinPermission( dto.getJoinPermission() );
        course.setSemester( dto.getSemester() );
        course.setStudentCount( dto.getStudentCount() );
        course.setTeacherName( dto.getTeacherName() );
        course.setCollege( deptSmallDtoToDept( dto.getCollege() ) );
        course.setSignCount( dto.getSignCount() );
        course.setSignHistory( signHistorySmallDtoListToSignHistoryList( dto.getSignHistory() ) );

        return course;
    }

    @Override
    public List<Course> toEntity(List<CourseDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( dtoList.size() );
        for ( CourseDto courseDto : dtoList ) {
            list.add( toEntity( courseDto ) );
        }

        return list;
    }

    @Override
    public List<CourseDto> toDto(List<Course> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseDto> list = new ArrayList<CourseDto>( entityList.size() );
        for ( Course course : entityList ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    @Override
    public CourseDto toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDto courseDto = new CourseDto();

        String nickName = courseCreateUserNickName( course );
        if ( nickName != null ) {
            courseDto.setUserName( nickName );
        }
        courseDto.setId( course.getId() );
        courseDto.setClassName( course.getClassName() );
        courseDto.setCourseName( course.getCourseName() );
        courseDto.setCourseCode( course.getCourseCode() );
        courseDto.setEnabled( course.getEnabled() );
        courseDto.setJoinPermission( course.getJoinPermission() );
        courseDto.setSemester( course.getSemester() );
        courseDto.setStudentCount( course.getStudentCount() );
        courseDto.setTeacherName( course.getTeacherName() );
        courseDto.setCollege( deptToDeptSmallDto( course.getCollege() ) );
        courseDto.setSignCount( course.getSignCount() );
        courseDto.setSignHistory( signHistoryListToSignHistorySmallDtoList( course.getSignHistory() ) );

        return courseDto;
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

    protected SignHistory signHistorySmallDtoToSignHistory(SignHistorySmallDto signHistorySmallDto) {
        if ( signHistorySmallDto == null ) {
            return null;
        }

        SignHistory signHistory = new SignHistory();

        signHistory.setId( signHistorySmallDto.getId() );
        signHistory.setAttendance( signHistorySmallDto.getAttendance() );
        signHistory.setAbsence( signHistorySmallDto.getAbsence() );
        signHistory.setCreateTime( signHistorySmallDto.getCreateTime() );

        return signHistory;
    }

    protected List<SignHistory> signHistorySmallDtoListToSignHistoryList(List<SignHistorySmallDto> list) {
        if ( list == null ) {
            return null;
        }

        List<SignHistory> list1 = new ArrayList<SignHistory>( list.size() );
        for ( SignHistorySmallDto signHistorySmallDto : list ) {
            list1.add( signHistorySmallDtoToSignHistory( signHistorySmallDto ) );
        }

        return list1;
    }

    private String courseCreateUserNickName(Course course) {
        if ( course == null ) {
            return null;
        }
        User createUser = course.getCreateUser();
        if ( createUser == null ) {
            return null;
        }
        String nickName = createUser.getNickName();
        if ( nickName == null ) {
            return null;
        }
        return nickName;
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

    protected SignHistorySmallDto signHistoryToSignHistorySmallDto(SignHistory signHistory) {
        if ( signHistory == null ) {
            return null;
        }

        SignHistorySmallDto signHistorySmallDto = new SignHistorySmallDto();

        signHistorySmallDto.setId( signHistory.getId() );
        signHistorySmallDto.setAttendance( signHistory.getAttendance() );
        signHistorySmallDto.setAbsence( signHistory.getAbsence() );
        signHistorySmallDto.setCreateTime( signHistory.getCreateTime() );

        return signHistorySmallDto;
    }

    protected List<SignHistorySmallDto> signHistoryListToSignHistorySmallDtoList(List<SignHistory> list) {
        if ( list == null ) {
            return null;
        }

        List<SignHistorySmallDto> list1 = new ArrayList<SignHistorySmallDto>( list.size() );
        for ( SignHistory signHistory : list ) {
            list1.add( signHistoryToSignHistorySmallDto( signHistory ) );
        }

        return list1;
    }
}
