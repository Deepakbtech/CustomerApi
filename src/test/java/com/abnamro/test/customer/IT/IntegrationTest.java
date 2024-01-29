package com.abnamro.test.customer.IT;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.abnamro.test.customer.models.Account.AccountDto;
import com.abnamro.test.customer.models.Address;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerIdentification;
import com.abnamro.test.customer.models.TokenRequest;
import com.abnamro.test.customer.models.TokenResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {

  RestTemplate restTemplate;

  @LocalServerPort private int port;

  @BeforeEach
  public void setUp() {
    restTemplate = new RestTemplate();
  }


  @Test
  public void shouldRegisterCustomer() {
    ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
        "http://localhost:" + port + "/register", HttpMethod.POST, new HttpEntity<>(
            Customer.builder().name("test").userName("test").address(
                    Address.builder().country("NL").build()).dateOfBirth(LocalDate.now().minusYears(20))
                .customerIdentification(
                    CustomerIdentification.builder().type("Passport").build()).build()),
        new ParameterizedTypeReference<>() {
        });
    assertNotNull(responseEntity.getBody());
    assertNotNull(responseEntity.getBody().userName());
    assertNotNull(responseEntity.getBody().password());
  }

  @Test
  public void shouldNotRegisterCustomerIncaseOfNotAllowedCountry() {
    try {
      restTemplate.exchange(
          "http://localhost:" + port + "/register", HttpMethod.POST, new HttpEntity<>(
              Customer.builder().name("test").userName("test").address(
                      Address.builder().country("IND").build())
                  .dateOfBirth(LocalDate.now().minusYears(20))
                  .customerIdentification(
                      CustomerIdentification.builder().type("Passport").build()).build()),
          new ParameterizedTypeReference<>() {
          });
    } catch (HttpClientErrorException ex) {
      assertTrue(ex.getResponseBodyAsString().contains("400"));
      assertTrue(ex.getMessage().contains(
          "address.country:Customer is outside allowed country and it is not allowed to register"));
    }
  }

  @Test
  public void shouldNotRegisterCustomerIncaseOfBelow18Years() {
    try {
      restTemplate.exchange(
          "http://localhost:" + port + "/register", HttpMethod.POST, new HttpEntity<>(
              Customer.builder().name("test").userName("test").address(
                      Address.builder().country("NL").build())
                  .dateOfBirth(LocalDate.now().minusYears(15))
                  .customerIdentification(
                      CustomerIdentification.builder().type("Passport").build()).build()),
          new ParameterizedTypeReference<>() {
          });
    } catch (HttpClientErrorException ex) {
      assertTrue(ex.getResponseBodyAsString().contains("400"));
      assertTrue(ex.getMessage().contains(
          "dateOfBirth:Customer is not allowed to register based on his age"));
    }
  }


  @Test
  public void shouldTestTooManyRequest() {
    try {
      ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
          "http://localhost:" + port + "/register", HttpMethod.POST, new HttpEntity<>(
              Customer.builder().name("test").userName("test").address(
                      Address.builder().country("NL").build())
                  .dateOfBirth(LocalDate.now().minusYears(20))
                  .customerIdentification(
                      CustomerIdentification.builder().type("Passport").build()).build()),
          new ParameterizedTypeReference<>() {
          });

      ResponseEntity<TokenResponse> tokenResponseResponseEntity = restTemplate.exchange(
          "http://localhost:" + port + "/token", HttpMethod.POST, new HttpEntity<>(
              new TokenRequest(responseEntity.getBody().userName(),
                  responseEntity.getBody().password(), null)),
          new ParameterizedTypeReference<>() {
          });

      ResponseEntity<String> validateTokenResponseEntity = restTemplate.exchange(
          "http://localhost:" + port + "/logon", HttpMethod.POST, new HttpEntity<>(
              new TokenRequest(null, null, tokenResponseResponseEntity.getBody().token())),
          new ParameterizedTypeReference<>() {
          });

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION,
          "Bearer " + tokenResponseResponseEntity.getBody().token());
      ResponseEntity<AccountDto> overviewResponseEntity = restTemplate.exchange(
          "http://localhost:" + port + "/overview", HttpMethod.GET, new HttpEntity<>(null, headers),
          new ParameterizedTypeReference<>() {
          });

    }catch (HttpClientErrorException ex){
      assertTrue(ex.getResponseBodyAsString().contains("429"));
      assertTrue(ex.getResponseBodyAsString().contains("Too many request"));
    }

  }
}
