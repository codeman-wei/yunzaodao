package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.service.dto.StudentSmallDto;
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
public class StudentSmallMapperImpl implements StudentSmallMapper {

    @Override
    public Student toEntity(StudentSmallDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( dto.getId() );
        student.setName( dto.getName() );
        student.setStudentNumber( dto.getStudentNumber() );

        return student;
    }

    @Override
    public StudentSmallDto toDto(Student entity) {
        if ( entity == null ) {
            return null;
        }

        StudentSmallDto studentSmallDto = new StudentSmallDto();

        studentSmallDto.setId( entity.getId() );
        studentSmallDto.setName( entity.getName() );
        studentSmallDto.setStudentNumber( entity.getStudentNumber() );

        return studentSmallDto;
    }

    @Override
    public List<Student> toEntity(List<StudentSmallDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( dtoList.size() );
        for ( StudentSmallDto studentSmallDto : dtoList ) {
            list.add( toEntity( studentSmallDto ) );
        }

        return list;
    }

    @Override
    public List<StudentSmallDto> toDto(List<Student> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudentSmallDto> list = new ArrayList<StudentSmallDto>( entityList.size() );
        for ( Student student : entityList ) {
            list.add( toDto( student ) );
        }

        return list;
    }
}
