package com.yzd.modules.study.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
* @author wdc
* @date 2020-05-05
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignHistoryMapper extends BaseMapper<SignHistoryDto, SignHistory> {

    @Override
    @Mappings({
            @Mapping(source = "entity.course.courseName", target = "courseName"),
            @Mapping(source = "entity.course.courseCode", target = "courseCode"),
            @Mapping(source = "entity.course.college.name", target = "collegeName"),
            @Mapping(source = "entity.course.teacherName", target = "teacherName")
    })
    SignHistoryDto toDto(SignHistory entity);
}