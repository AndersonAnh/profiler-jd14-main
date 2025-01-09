package by.javaguru.profiler.usecasses.dto;

import lombok.Builder;

import java.time.YearMonth;

@Builder(setterPrefix = "with")
public record CourseResponseDto(
        Integer sequenceNumber,
        YearMonth periodFrom,
        YearMonth periodTo,
        Boolean presentTime,
        String school,
        String courseName,
        String description,
        String certificateUrl
) {
}
