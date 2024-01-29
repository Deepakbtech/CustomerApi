package com.abnamro.test.customer.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public record TokenRequest(String userName, String password,
                           @JsonInclude(Include.NON_NULL) String token) {


}
