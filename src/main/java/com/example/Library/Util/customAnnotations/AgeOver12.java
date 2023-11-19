package com.example.Library.Util.customAnnotations;

import com.example.Library.Util.customAnnotationsValidators.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
@Documented
public @interface AgeOver12 {
    String message() default "Age must be over 12";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
