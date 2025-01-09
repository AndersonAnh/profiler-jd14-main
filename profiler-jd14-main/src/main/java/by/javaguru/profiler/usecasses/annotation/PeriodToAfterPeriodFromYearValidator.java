package by.javaguru.profiler.usecasses.annotation;

import by.javaguru.profiler.usecasses.util.Periodic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class PeriodToAfterPeriodFromYearValidator
        implements ConstraintValidator<PeriodToAfterPeriodFromValidation, Periodic<Year>> {

    @Override
    public boolean isValid(Periodic<Year> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if (value.periodTo() != null && value.periodFrom() != null) {
            return value.periodFrom().isBefore(value.periodTo());
        }
        return true;
    }

}
