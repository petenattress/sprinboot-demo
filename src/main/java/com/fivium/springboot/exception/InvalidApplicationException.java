package com.fivium.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such application could be found")
public class InvalidApplicationException extends RuntimeException {
  public InvalidApplicationException(String message) {
    super(message);
  }
}
