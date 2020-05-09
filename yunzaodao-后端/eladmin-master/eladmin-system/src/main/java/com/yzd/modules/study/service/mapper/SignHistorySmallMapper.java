package com.yzd.modules.study.service.mapper;

import com.yzd.base.BaseMapper;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.SignHistorySmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignHistorySmallMapper extends BaseMapper<SignHistorySmallDto, SignHistory> {
}
