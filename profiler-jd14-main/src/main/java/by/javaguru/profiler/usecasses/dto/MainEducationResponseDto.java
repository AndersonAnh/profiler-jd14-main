package by.javaguru.profiler.usecasses.dto;

import lombok.Builder;

import java.time.Year;

@Builder(setterPrefix = "with")
public record MainEducationResponseDto(
        Integer sequenceNumber,
        Year periodFrom,
        Year periodTo,
        Boolean presentTime,
        String institution,
        String faculty,
        String specialty
) {
}
