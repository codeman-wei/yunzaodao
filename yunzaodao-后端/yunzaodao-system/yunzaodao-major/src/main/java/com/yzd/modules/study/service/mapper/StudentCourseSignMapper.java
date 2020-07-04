package com.yzd.modules.study.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.study.domain.StudentCourseSign;
import com.yzd.modules.study.service.dto.StudentCourseSignDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author wdc
* @date 2020-05-05
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentCourseSignMapper extends BaseMapper<StudentCourseSignDto, StudentCourseSign> {

}