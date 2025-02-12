package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.usecasses.annotation.DateBottomLimitValidation;
import by.javaguru.profiler.usecasses.annotation.DateUpperLimitFromNowValidation;
import by.javaguru.profiler.usecasses.util.Periodic;
import by.javaguru.profiler.usecasses.util.Sequencable;
import by.javaguru.profiler.usecasses.util.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.time.YearMonth;

@Builder(setterPrefix = "with")
public record CourseRequestDto(
        @NotNull(message = "Sequence number must not be null")
        @Min(value = 1, message = "Sequence number must not be less than 1")
        @Max(value = 5, message = "Sequence number must not be more than 5")
        @Schema(defaultValue = "1", description = "Sequence number")
        Integer sequenceNumber,
        @PastOrPresent(message = "Date is in the future")
        @DateBottomLimitValidation(value = "1970-01")
        @NotNull(message = "Course period start must not be null")
        @Schema(defaultValue = "2020-01", description = "Course period from")
        YearMonth periodFrom,
        @DateUpperLimitFromNowValidation(value = 2L)
        @Schema(defaultValue = "2020-05", description = "Course period to")
        YearMonth periodTo,
        @NotNull(message = "Must be specified")
        @Schema(defaultValue = "false", description = "Is course up to now")
        Boolean presentTime,
        @NotNull(message = "School name must not be null")
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_SIXTH_PAGE_TEXT_FIELDS, message = "Invalid school name")
        @Length(max = 40, message = "School name is too long, the max number of symbols is 40")
        @Schema(defaultValue = "School Name", description = "School Name")
        String school,
        @NotNull(message = "Course name must not be null")
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_SIXTH_PAGE_TEXT_FIELDS, message = "Invalid course name")
        @Length(max = 40, message = "Course name is too long, the max number of symbols is 40")
        @Schema(defaultValue = "Course Name", description = "Course Name")
        String courseName,
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_SIXTH_PAGE_TEXT_FIELDS, message = "Invalid description")
        @Length(max = 130, message = "Description is too long, the max number of symbols is 130")
        @Schema(defaultValue = "Description", description = "Course description")
        String description,
        @Length(max = 255, message = "Certificate URL is too long, the max number of symbols is 255")
        @Schema(defaultValue = "http://example.com/link", description = "Certificate URL")
        String certificateUrl
) implements Sequencable, Periodic<YearMonth> {
}
