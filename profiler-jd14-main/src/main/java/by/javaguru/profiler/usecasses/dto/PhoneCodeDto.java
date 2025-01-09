package by.javaguru.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PhoneCodeDto(
        Long id,
        Integer code,
        CountryDto country
) {
}