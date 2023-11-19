package com.example.Library.Util.customAnnotationsValidators;

import com.example.Library.Util.customAnnotations.ValidString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringEmptinessValidator implements ConstraintValidator<ValidString, String> {

        @Override
        public void initialize(ValidString constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value != null && !value.trim().isEmpty();
        }

}
