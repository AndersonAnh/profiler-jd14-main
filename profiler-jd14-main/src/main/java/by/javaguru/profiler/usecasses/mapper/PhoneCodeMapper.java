package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.PhoneCode;
import by.javaguru.profiler.usecasses.dto.PhoneCodeDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = CountryMapper.class
)
public interface PhoneCodeMapper {

    List<PhoneCodeDto> toDto(List<PhoneCode> phoneCodes);
}
