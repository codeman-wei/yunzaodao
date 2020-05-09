package com.yzd.modules.study.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.service.dto.StudentSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentSmallMapper extends BaseMapper<StudentSmallDto, Student> {
}
