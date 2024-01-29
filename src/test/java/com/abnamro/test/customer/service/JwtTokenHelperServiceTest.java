package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.service.impl.JwtTokenHelperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class JwtTokenHelperServiceTest {

  JwtTokenHelperService jwtTokenService;

  @BeforeEach
  public void setUp() {
    jwtTokenService = new JwtTokenHelperService("secret");
  }

  @Test
  public void shouldGenerateTokenWithUserAndExtractUserName() {
    String userName = "test";
    var token = jwtTokenService.generateToken(userName);
    assertNotNull(token);

    var retrieveUserName = jwtTokenService.extractUsername(token);
    assertEquals(userName, retrieveUserName);

    assertTrue(jwtTokenService.isValidToken(token));
  }

  @Test
  public void shouldThrowExceptionIncaseOfInvalidToken() {
    assertThrows(UnAuthorizedException.class,
        () -> jwtTokenService.extractUsername("asdadadadadadadada"));

  }

}
