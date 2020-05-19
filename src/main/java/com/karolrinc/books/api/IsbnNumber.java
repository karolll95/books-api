package com.karolrinc.books.api;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsbnNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsbnNumber {
    String message() default "Invalid ISBN number format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
