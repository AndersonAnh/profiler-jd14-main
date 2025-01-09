package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Country;
import by.javaguru.profiler.usecasses.dto.CountryDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CountryMapper {

    List<CountryDto> toDto(List<Country> countries);

    CountryDto toDto(Country country);

}
