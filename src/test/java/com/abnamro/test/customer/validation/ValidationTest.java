package com.abnamro.test.customer.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abnamro.test.customer.config.ApplicationContextHolder;
import com.abnamro.test.customer.config.CountryListProperty;
import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.models.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ValidationTest {

  @Mock
  private CountryListProperty countryListProperty;

  @Mock
  private ApplicationContextHolder applicationContextHolder;


  @Test
  public void shouldThrowOnCreatingWithInValidData() {

    Customer customer = Customer.builder().dateOfBirth(LocalDate.now().minusYears(2)).build();

    Set<ConstraintViolation<Customer>> violationSet =
        Validation.buildDefaultValidatorFactory().getValidator().validate(customer);

    violationSet.forEach(System.out::println);

    assertEquals(3, violationSet.size()); // 3 more for expected results

    assertEquals(
        1,
        violationSet.stream()
            .filter(
                violation ->
                    violation
                        .getPropertyPath()
                        .toString()
                        .endsWith("dateOfBirth"))
            .count());

    assertEquals(
        1,
        violationSet.stream()
            .filter(
                violation ->
                    violation
                        .getPropertyPath()
                        .toString()
                        .endsWith("userName"))
            .count());

    assertEquals(
        1,
        violationSet.stream()
            .filter(
                violation ->
                    violation
                        .getPropertyPath()
                        .toString()
                        .endsWith("name"))
            .count());
  }

}
