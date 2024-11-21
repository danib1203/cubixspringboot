package hu.cubix.hr.mapper;

import hu.cubix.hr.dto.TimeoffDto;
import hu.cubix.hr.model.Timeoff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeoffMapper {

    @Mapping(target = "requestBy.company.employees", ignore = true)
    @Mapping(target = "requestBy.position", ignore = true)
    @Mapping(target = "acceptedBy.company", ignore = true)
    @Mapping(target = "acceptedBy.position", ignore = true)
    TimeoffDto timeoffToDto(Timeoff timeoff);

    List<TimeoffDto> timeoffsToDtos(List<Timeoff> timeoffs);

    Timeoff dtoToTimeoff(TimeoffDto timeoffDto);

    List<Timeoff> dtosToTimeoffs(List<TimeoffDto> timeoffDtos);


    default Page<TimeoffDto> timeoffsToDtosPage(Page<Timeoff> timeoffPage) {
        List<TimeoffDto> timeoffDtos = timeoffsToDtos(timeoffPage.getContent());
        return new PageImpl<>(timeoffDtos, timeoffPage.getPageable(),
                timeoffPage.getTotalElements());
    }
}
