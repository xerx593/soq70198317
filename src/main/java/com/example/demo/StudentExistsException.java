package com.example.demo;

public class StudentExistsException extends RuntimeException {

  public StudentExistsException(String msg) {
    super(msg);
  }
}
