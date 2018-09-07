package com.fivium.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Authentication session is invalid")
public class InvalidAuthenticationException extends RuntimeException {
  public InvalidAuthenticationException(String message) {
    super(message);
  }
}
