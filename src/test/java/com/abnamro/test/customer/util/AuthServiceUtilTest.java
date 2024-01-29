package com.abnamro.test.customer.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(SpringExtension.class)
public class AuthServiceUtilTest {

  @BeforeEach
  public void setUp() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Test");
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  public void shouldFindAuthorizationHeader() {
    var authValue = AuthServiceUtil.retrieveHeader("Authorization");
    assertNotNull(authValue);
    assertEquals("Test", authValue);
  }

  @Test
  public void shouldReturnEmptyForInvalidHeader() {
    var authValue = AuthServiceUtil.retrieveHeader("test");
    assertNull(authValue);
  }
}
