package com.yzd.modules.study.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.service.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
* @author wdc
* @date 2020-05-03
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper extends BaseMapper<StudentDto, Student> {

    @Mapping(source = "experience", target = "experience")
    StudentDto toDto(Student entity, Integer experience);
}