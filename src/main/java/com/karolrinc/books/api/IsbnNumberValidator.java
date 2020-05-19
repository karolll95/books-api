package com.karolrinc.books.api;

import lombok.NonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsbnNumberValidator implements ConstraintValidator<IsbnNumber, String> {
    private static final String ISBN_NUMBER_REGEX =
            "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|" +
            "(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";

    @Override
    public boolean isValid(@NonNull final String isbnNumber, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile(ISBN_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(isbnNumber);

        return matcher.matches();
    }

    @Override
    public void initialize(IsbnNumber constraintAnnotation) {

    }
}
