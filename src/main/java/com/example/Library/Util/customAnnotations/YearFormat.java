package com.example.Library.Util.customAnnotations;

import com.example.Library.Util.customAnnotationsValidators.YearFormatValidator;
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
