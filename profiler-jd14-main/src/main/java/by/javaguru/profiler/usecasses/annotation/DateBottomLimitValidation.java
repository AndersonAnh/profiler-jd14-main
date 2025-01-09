package by.javaguru.profiler.usecasses.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {DateBottomLimitYearMonthValidator.class, DateBottomLimitYearValidator.class})
public @interface DateBottomLimitValidation {

    String value() default "";

    String message() default "Date should not be earlier than {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
