package com.abnamro.test.customer.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.TokenRequest;
import com.abnamro.test.customer.service.CustomerCredentialsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TokenControllerTest {

  @Autowired
  TokenController tokenController;

  @Autowired
  CustomerCredentialsService customerCredentialsService;

  @Test
  public void shouldRetrieveToken() {
    customerCredentialsService.create("testtoken");
    assertNotNull(tokenController.retrieveToken(new TokenRequest("testtoken", "Welcome!123", null)));
  }


  @Test
  public void shouldThrowUnAuthorizedForInvalidCredentials() {
    customerCredentialsService.create("testtoken1");
    assertThrows(UnAuthorizedException.class,
        () -> tokenController.retrieveToken(new TokenRequest("testinvalid", "test1", null)));
  }

  @Test
  public void shouldThrowUnAuthorizedForInvalidUserName() {
    customerCredentialsService.create("testtoken2");
    assertThrows(UnAuthorizedException.class,
        () -> tokenController.retrieveToken(new TokenRequest("test1", "Welcome!123", null)));
  }


}
