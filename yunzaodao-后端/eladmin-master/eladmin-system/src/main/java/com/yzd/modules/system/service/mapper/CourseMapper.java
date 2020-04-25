package com.yzd.modules.system.service.mapper;

import com.yzd.modules.system.domain.Course;
import com.yzd.modules.system.service.dto.CourseDto;
import com.yzd.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
* @author wdc
* @date 2020-03-20
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends BaseMapper<CourseDto, Course> {
    @Override
    @Mapping(source = "course.createUser.nickName",target = "userName")
    CourseDto toDto(Course course);
}