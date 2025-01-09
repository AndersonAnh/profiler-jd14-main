package by.javaguru.profiler.usecasses.annotation;

import by.javaguru.profiler.usecasses.util.Periodic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PresentTimePeriodToValidator implements ConstraintValidator<PresentTimePeriodToValidation, Periodic<?>> {

    @Override
    public boolean isValid(Periodic<?> value, ConstraintValidatorContext context) {
        if (value == null) return true;

        if (Boolean.TRUE.equals(value.presentTime())) {
            return isNull(value.periodTo());
        } else {
            return nonNull(value.periodTo());
        }
    }

}
