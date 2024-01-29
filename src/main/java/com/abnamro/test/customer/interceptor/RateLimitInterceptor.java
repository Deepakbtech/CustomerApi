package com.abnamro.test.customer.interceptor;

import com.abnamro.test.customer.exception.TooManyRequestException;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

  private final Bucket bucketLimit;

  private final List<String> ENDPOINTS_HITTING_DB = List.of("/register", "/overview", "/token");

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    if (ENDPOINTS_HITTING_DB.contains(request.getRequestURI())) {
      log.debug("Available limit for requests: {}", bucketLimit.getAvailableTokens());
      ConsumptionProbe probe = bucketLimit.tryConsumeAndReturnRemaining(1);
      if (!probe.isConsumed()) {
        log.warn("Too Many request, so no further processing");
        throw new TooManyRequestException("Too many request");
      } else {
        return true;
      }
    }
    return true;
  }

}
