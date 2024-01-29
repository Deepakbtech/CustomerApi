package com.abnamro.test.customer.service;

import com.abnamro.test.customer.models.TokenRequest;
import com.abnamro.test.customer.models.TokenResponse;

public interface TokenService {

  TokenResponse retrieveToken(TokenRequest tokenRequest);

  Boolean validateToken(String token);
}
