package com.yzd.modules.study.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.study.domain.UserSysVal;
import com.yzd.modules.study.service.dto.UserSysValDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author wdc
* @date 2020-05-13
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSysValMapper extends BaseMapper<UserSysValDto, UserSysVal> {

}