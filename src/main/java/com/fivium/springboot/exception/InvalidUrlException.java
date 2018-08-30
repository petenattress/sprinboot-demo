package com.fivium.springboot.exception;

public class InvalidUrlException extends RuntimeException {
  public InvalidUrlException(String message) {
    super(message);
  }
}
