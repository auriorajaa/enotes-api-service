package com.example.enotes.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.enotes.handler.GenericResponse;

public class CommonUtil {

  public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {

    GenericResponse response = GenericResponse.builder()
        .responseStatus(status)
        .status("success")
        .message("success")
        .data(data)
        .build();

    return response.create();
  }

  public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status) {

    GenericResponse response = GenericResponse.builder()
        .responseStatus(status)
        .status("success")
        .message(message)
        .build();

    return response.create();
  }

  public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {

    GenericResponse response = GenericResponse.builder()
        .responseStatus(status)
        .status("failed")
        .message("failed")
        .data(data)
        .build();

    return response.create();
  }

  public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {

    GenericResponse response = GenericResponse.builder()
        .responseStatus(status)
        .status("failed")
        .message(message)
        .build();

    return response.create();
  }

}
