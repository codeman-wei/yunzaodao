package com.yzd.modules.system.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.system.domain.Role;
import com.yzd.modules.system.service.dto.RoleSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDto, Role> {

}
