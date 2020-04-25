package com.yzd.modules.system.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.system.domain.Major;
import com.yzd.modules.system.service.dto.MajorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<MajorDto, Major> {

    /**
     * 转Dto
     * @param major 原始数据
     * @param deptSuperiorName /
     * @return /
     */
    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    MajorDto toDto(Major major, String deptSuperiorName);
}