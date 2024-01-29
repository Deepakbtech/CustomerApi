package com.abnamro.test.customer.service.impl;

import com.abnamro.test.customer.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenHelperService {


  private String clientSecret;
  private static final long EXPIRATION_TIME = 5 * 60 * 1000;

  public JwtTokenHelperService(@Value("client.secret") String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, clientSecret)
        .compact();
  }

  public String extractUsername(String token) {
    return extractBody(token)
        .getSubject();
  }

  private Claims extractBody(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(clientSecret)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      throw new UnAuthorizedException("Expired or invalid token");
    }
  }

  public boolean isValidToken(String token) {
    final Date expiration = extractBody(token).getExpiration();
    return !expiration.before(new Date());
  }

}
