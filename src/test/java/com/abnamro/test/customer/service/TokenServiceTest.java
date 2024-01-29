package com.abnamro.test.customer.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.Customer;
import com.abnamro.test.customer.models.CustomerCredentials;
import com.abnamro.test.customer.models.TokenRequest;
import com.abnamro.test.customer.service.impl.JwtTokenHelperService;
import com.abnamro.test.customer.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TokenServiceTest {

  @Mock
  private CustomerCredentialsService customerCredentialsService;

  @Mock
  private JwtTokenHelperService jwtTokenHelperService;

  TokenService tokenService;

  @BeforeEach
  public void setUp() {
    tokenService = new TokenServiceImpl(customerCredentialsService, jwtTokenHelperService);
  }

  @Test
  public void shouldReturnToken() {
    when(customerCredentialsService.retrieve(any())).thenReturn(
        CustomerCredentials.builder().userName("test").password("test1").build());
    when(jwtTokenHelperService.generateToken(any())).thenReturn(
        "sdsakjdnakjsdnjakndkjandjkandjksanda");
    var token = tokenService.retrieveToken(new TokenRequest("test", "test1", null));
    assertNotNull(token);
  }

  @Test
  public void shouldThrowUnAuthorizedWhenUserNotFound() {
    when(customerCredentialsService.retrieve(any())).thenThrow(new NotFoundException("Not found"));
    when(jwtTokenHelperService.generateToken(any())).thenReturn(
        "sdsakjdnakjsdnjakndkjandjkandjksanda");
    assertThrows(UnAuthorizedException.class,
        () -> tokenService.retrieveToken(new TokenRequest("test", "test1", null)));
  }

  @Test
  public void shouldThrowUnAuthorizedForInvalidCredentials() {
    when(customerCredentialsService.retrieve(any())).thenReturn(
        CustomerCredentials.builder().userName("test").password("test2").build());
    when(jwtTokenHelperService.generateToken(any())).thenReturn(
        "sdsakjdnakjsdnjakndkjandjkandjksanda");
    assertThrows(UnAuthorizedException.class,
        () -> tokenService.retrieveToken(new TokenRequest("test", "test1", null)));
  }

  @Test
  public void shouldReturnTrueForValidToken() {
    when(jwtTokenHelperService.isValidToken(any())).thenReturn(true);
    assertTrue(tokenService.validateToken("jshdndjkandjkasnjkdnsa"));
  }

  @Test
  public void shouldRetunrFalseForInValidToken() {
    when(jwtTokenHelperService.isValidToken(any())).thenReturn(false);
    assertFalse(tokenService.validateToken("jshdndjkandjkasnjkdnsa"));
  }
}
