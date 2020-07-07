package com.yzd.modules.study.service.mapper;

import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.SignHistorySmallDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-30T21:27:54+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
@Component
public class SignHistorySmallMapperImpl implements SignHistorySmallMapper {

    @Override
    public SignHistory toEntity(SignHistorySmallDto dto) {
        if ( dto == null ) {
            return null;
        }

        SignHistory signHistory = new SignHistory();

        signHistory.setId( dto.getId() );
        signHistory.setAttendance( dto.getAttendance() );
        signHistory.setAbsence( dto.getAbsence() );
        signHistory.setCreateTime( dto.getCreateTime() );

        return signHistory;
    }

    @Override
    public SignHistorySmallDto toDto(SignHistory entity) {
        if ( entity == null ) {
            return null;
        }

        SignHistorySmallDto signHistorySmallDto = new SignHistorySmallDto();

        signHistorySmallDto.setId( entity.getId() );
        signHistorySmallDto.setAttendance( entity.getAttendance() );
        signHistorySmallDto.setAbsence( entity.getAbsence() );
        signHistorySmallDto.setCreateTime( entity.getCreateTime() );

        return signHistorySmallDto;
    }

    @Override
    public List<SignHistory> toEntity(List<SignHistorySmallDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SignHistory> list = new ArrayList<SignHistory>( dtoList.size() );
        for ( SignHistorySmallDto signHistorySmallDto : dtoList ) {
            list.add( toEntity( signHistorySmallDto ) );
        }

        return list;
    }

    @Override
    public List<SignHistorySmallDto> toDto(List<SignHistory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SignHistorySmallDto> list = new ArrayList<SignHistorySmallDto>( entityList.size() );
        for ( SignHistory signHistory : entityList ) {
            list.add( toDto( signHistory ) );
        }

        return list;
    }
}
