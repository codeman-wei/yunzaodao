package com.yzd.modules.system.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.system.domain.Menu;
import com.yzd.modules.system.service.dto.MenuDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {

}
