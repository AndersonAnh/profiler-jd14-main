package by.javaguru.profiler.usecasses.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PeriodToAfterOrEqualToPeriodFromYearValidator.class,
        PeriodToAfterOrEqualToPeriodFromYearMonthValidator.class})
public @interface PeriodToAfterOrEqualToPeriodFromValidation {

    String message() default "Field `periodTo` should be later than or equal to `periodFrom`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
