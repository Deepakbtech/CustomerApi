package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.exception.NotFoundException;
import com.abnamro.test.customer.exception.UnAuthorizedException;
import com.abnamro.test.customer.models.CustomerCredentials;
import com.abnamro.test.customer.models.TokenRequest;
import com.abnamro.test.customer.models.TokenResponse;
import com.abnamro.test.customer.service.CustomerCredentialsService;
import com.abnamro.test.customer.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private final CustomerCredentialsService customerCredentialsService;

  private final JwtTokenHelperService jwtTokenHelperService;

  @Override
  public TokenResponse retrieveToken(TokenRequest tokenRequest) {
    CustomerCredentials customerCredentials;
    try {
      customerCredentials = customerCredentialsService.retrieve(
          tokenRequest.userName());
    }catch (NotFoundException e){
      throw new UnAuthorizedException("Unauthorized access");
    }

    if (!(customerCredentials.getPassword().equalsIgnoreCase(
        tokenRequest.password()))) {
      throw new UnAuthorizedException("Unauthorized access");
    }
    return new TokenResponse(tokenRequest.userName(), tokenRequest.password(),
        jwtTokenHelperService.generateToken(tokenRequest.userName()));
  }

  @Override
  public Boolean validateToken(String token) {
    return jwtTokenHelperService.isValidToken(token);
  }
}
