package com.abnamro.test.customer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CountryValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCountry {

  String message() default "Customer is outside allowed country and it is not allowed to register";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
