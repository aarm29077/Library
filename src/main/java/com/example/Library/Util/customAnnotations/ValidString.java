package com.example.Library.Util.customAnnotations;

import com.example.Library.Util.customAnnotationsValidators.StringEmptinessValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringEmptinessValidator.class)
public @interface ValidString {
    String message() default "Invalid input: must not be empty or contain only whitespace.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
