package com.abnamro.test.customer.controller;

import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.TokenRequest;
import com.abnamro.test.customer.models.TokenResponse;
import com.abnamro.test.customer.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

  private final TokenService tokenService;

  @PostMapping("/token")
  public ResponseEntity<TokenResponse> retrieveToken(
      @RequestBody TokenRequest tokenRequest) {

    return ResponseEntity.ok(tokenService.retrieveToken(tokenRequest));
  }

  @PostMapping("/logon")
  public ResponseEntity<String> logon(
      @RequestBody TokenRequest tokenRequest) {

    if (tokenService.validateToken(tokenRequest.token())) {
      return ResponseEntity.ok("Login Success");
    }
    throw new UnAuthorizedException("Unauthorized");
  }

}
