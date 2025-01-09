package by.javaguru.profiler.usecasses.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;
import java.time.YearMonth;

public class DateUpperLimitFromNowYearMonthValidator
        implements ConstraintValidator<DateUpperLimitFromNowValidation, YearMonth> {

    private long increment;

    @Override
    public void initialize(DateUpperLimitFromNowValidation constraintAnnotation) {
        this.increment = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(YearMonth value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Year maxYear = Year.now().plusYears(increment);
        YearMonth maxYearMonth = YearMonth.of(maxYear.getValue(), 12);
        return value.isBefore(maxYearMonth) || value.equals(maxYearMonth);
    }

}
