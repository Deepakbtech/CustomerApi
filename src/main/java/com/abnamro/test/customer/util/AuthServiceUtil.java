package com.abnamro.test.customer.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthServiceUtil {

  public static String retrieveHeader(String headerName) {
    var optRequest = findCurrentServletRequest();
    return optRequest.map(request -> request.getHeader(headerName)).orElse(null);
  }


  public static Optional<HttpServletRequest> findCurrentServletRequest() {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .filter(ServletRequestAttributes.class::isInstance)
        .map(ServletRequestAttributes.class::cast)
        .map(ServletRequestAttributes::getRequest);
  }
}
