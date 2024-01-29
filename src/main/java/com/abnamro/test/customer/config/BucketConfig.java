package com.abnamro.test.customer.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

  @Bean
  public Bucket bucketLimit() {
    Bandwidth limit = Bandwidth.classic(2, Refill.intervally(1, Duration.ofSeconds(1)));
    return Bucket.builder()
        .addLimit(limit)
        .build();
  }
}
