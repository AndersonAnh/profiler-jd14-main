package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.Country;
import lombok.Builder;

/**
 * A DTO for the {@link Country} entity
 */
@Builder(setterPrefix = "with")
public record CountryDto(
        Long id,
        String countryName
) {
}