package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.domain.StudentCourseSign;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.modules.study.service.dto.StudentCourseSignDto;
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
public class StudentCourseSignMapperImpl implements StudentCourseSignMapper {

    @Override
    public StudentCourseSign toEntity(StudentCourseSignDto dto) {
        if ( dto == null ) {
            return null;
        }

        StudentCourseSign studentCourseSign = new StudentCourseSign();

        studentCourseSign.setId( dto.getId() );
        studentCourseSign.setSignHistory( signHistoryDtoToSignHistory( dto.getSignHistory() ) );
        studentCourseSign.setStudent( studentSmallDtoToStudent( dto.getStudent() ) );
        studentCourseSign.setAttendance( dto.getAttendance() );
        studentCourseSign.setReplenish( dto.getReplenish() );
        studentCourseSign.setCreateTime( dto.getCreateTime() );

        return studentCourseSign;
    }

    @Override
    public StudentCourseSignDto toDto(StudentCourseSign entity) {
        if ( entity == null ) {
            return null;
        }

        StudentCourseSignDto studentCourseSignDto = new StudentCourseSignDto();

        studentCourseSignDto.setId( entity.getId() );
        studentCourseSignDto.setSignHistory( signHistoryToSignHistoryDto( entity.getSignHistory() ) );
        studentCourseSignDto.setStudent( studentToStudentSmallDto( entity.getStudent() ) );
        studentCourseSignDto.setAttendance( entity.getAttendance() );
        studentCourseSignDto.setReplenish( entity.getReplenish() );
        studentCourseSignDto.setCreateTime( entity.getCreateTime() );

        return studentCourseSignDto;
    }

    @Override
    public List<StudentCourseSign> toEntity(List<StudentCourseSignDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StudentCourseSign> list = new ArrayList<StudentCourseSign>( dtoList.size() );
        for ( StudentCourseSignDto studentCourseSignDto : dtoList ) {
            list.add( toEntity( studentCourseSignDto ) );
        }

        return list;
    }

    @Override
    public List<StudentCourseSignDto> toDto(List<StudentCourseSign> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudentCourseSignDto> list = new ArrayList<StudentCourseSignDto>( entityList.size() );
        for ( StudentCourseSign studentCourseSign : entityList ) {
            list.add( toDto( studentCourseSign ) );
        }

        return list;
    }

    protected SignHistory signHistoryDtoToSignHistory(SignHistoryDto signHistoryDto) {
        if ( signHistoryDto == null ) {
            return null;
        }

        SignHistory signHistory = new SignHistory();

        signHistory.setId( signHistoryDto.getId() );
        signHistory.setAttendance( signHistoryDto.getAttendance() );
        signHistory.setAbsence( signHistoryDto.getAbsence() );
        signHistory.setCreateTime( signHistoryDto.getCreateTime() );

        return signHistory;
    }

    protected Student studentSmallDtoToStudent(StudentSmallDto studentSmallDto) {
        if ( studentSmallDto == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentSmallDto.getId() );
        student.setName( studentSmallDto.getName() );
        student.setStudentNumber( studentSmallDto.getStudentNumber() );

        return student;
    }

    protected SignHistoryDto signHistoryToSignHistoryDto(SignHistory signHistory) {
        if ( signHistory == null ) {
            return null;
        }

        SignHistoryDto signHistoryDto = new SignHistoryDto();

        signHistoryDto.setId( signHistory.getId() );
        signHistoryDto.setAttendance( signHistory.getAttendance() );
        signHistoryDto.setAbsence( signHistory.getAbsence() );
        signHistoryDto.setCreateTime( signHistory.getCreateTime() );

        return signHistoryDto;
    }

    protected StudentSmallDto studentToStudentSmallDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentSmallDto studentSmallDto = new StudentSmallDto();

        studentSmallDto.setId( student.getId() );
        studentSmallDto.setName( student.getName() );
        studentSmallDto.setStudentNumber( student.getStudentNumber() );

        return studentSmallDto;
    }
}
