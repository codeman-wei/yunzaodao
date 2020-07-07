package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.service.dto.StudentDto;
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
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(StudentDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( dto.getId() );
        student.setName( dto.getName() );
        student.setStudentNumber( dto.getStudentNumber() );
        student.setCreateTime( dto.getCreateTime() );
        student.setPassword( dto.getPassword() );
        student.setCollege( deptSmallDtoToDept( dto.getCollege() ) );
        student.setEmail( dto.getEmail() );
        student.setEnabled( dto.getEnabled() );
        student.setPhone( dto.getPhone() );
        student.setLastPasswordResetTime( dto.getLastPasswordResetTime() );
        student.setSex( dto.getSex() );

        return student;
    }

    @Override
    public StudentDto toDto(Student entity) {
        if ( entity == null ) {
            return null;
        }

        StudentDto studentDto = new StudentDto();

        studentDto.setId( entity.getId() );
        studentDto.setName( entity.getName() );
        studentDto.setStudentNumber( entity.getStudentNumber() );
        studentDto.setPassword( entity.getPassword() );
        studentDto.setCreateTime( entity.getCreateTime() );
        studentDto.setCollege( deptToDeptSmallDto( entity.getCollege() ) );
        studentDto.setEmail( entity.getEmail() );
        studentDto.setEnabled( entity.getEnabled() );
        studentDto.setPhone( entity.getPhone() );
        studentDto.setLastPasswordResetTime( entity.getLastPasswordResetTime() );
        studentDto.setSex( entity.getSex() );

        return studentDto;
    }

    @Override
    public List<Student> toEntity(List<StudentDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( dtoList.size() );
        for ( StudentDto studentDto : dtoList ) {
            list.add( toEntity( studentDto ) );
        }

        return list;
    }

    @Override
    public List<StudentDto> toDto(List<Student> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudentDto> list = new ArrayList<StudentDto>( entityList.size() );
        for ( Student student : entityList ) {
            list.add( toDto( student ) );
        }

        return list;
    }

    @Override
    public StudentDto toDto(Student entity, Integer experience) {
        if ( entity == null && experience == null ) {
            return null;
        }

        StudentDto studentDto = new StudentDto();

        if ( entity != null ) {
            studentDto.setId( entity.getId() );
            studentDto.setName( entity.getName() );
            studentDto.setStudentNumber( entity.getStudentNumber() );
            studentDto.setPassword( entity.getPassword() );
            studentDto.setCreateTime( entity.getCreateTime() );
            studentDto.setCollege( deptToDeptSmallDto( entity.getCollege() ) );
            studentDto.setEmail( entity.getEmail() );
            studentDto.setEnabled( entity.getEnabled() );
            studentDto.setPhone( entity.getPhone() );
            studentDto.setLastPasswordResetTime( entity.getLastPasswordResetTime() );
            studentDto.setSex( entity.getSex() );
        }
        if ( experience != null ) {
            studentDto.setExperience( experience );
        }

        return studentDto;
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
