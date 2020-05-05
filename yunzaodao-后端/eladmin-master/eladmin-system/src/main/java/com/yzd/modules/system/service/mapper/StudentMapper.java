package com.yzd.modules.system.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.system.domain.Student;
import com.yzd.modules.system.service.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import javax.xml.transform.Source;

/**
* @author wdc
* @date 2020-05-03
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper extends BaseMapper<StudentDto, Student> {

    @Mapping(source = "experience", target = "experience")
    StudentDto toDto(Student entity, Integer experience);
}