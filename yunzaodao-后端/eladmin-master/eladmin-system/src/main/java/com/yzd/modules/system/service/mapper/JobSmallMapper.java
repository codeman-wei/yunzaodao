package com.yzd.modules.system.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.system.domain.Major;
import com.yzd.modules.system.service.dto.MajorSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobSmallMapper extends BaseMapper<MajorSmallDto, Major> {

}