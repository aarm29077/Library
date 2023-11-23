package com.example.Library.util.customAnnotations;

import com.example.Library.util.customAnnotationsValidators.YearFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearFormatValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearFormat {
    String message() default "Invalid year format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
