package com.example.enotes.util;

import com.example.enotes.config.security.CustomUserDetails;
import com.example.enotes.dto.UserResponse;
import com.example.enotes.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.enotes.handler.GenericResponse;
import org.springframework.security.core.context.SecurityContextHolder;

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

  public static String getContentType(String originalFileName) {

    String extension = FilenameUtils.getExtension(originalFileName);

    switch (extension) {
      case "pdf":
        return "application/pdf";
      case "xlsx":
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      case "txt":
        return "text/plain";
      case "docx":
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
      case "png":
        return "image/png";
      case "jpg":
        return "image/jpeg";
      case "jpeg":
        return "image/jpeg";
      default:
        return "application/octet-stream";
    }

  }

    public static String getUrl(HttpServletRequest request) {
      String apiUrl = request.getRequestURL().toString();
      // String url = request.getServletPath();
      apiUrl = apiUrl.replace(request.getServletPath(), "");

      return apiUrl;
    }

    public static User getLoggedInUser() {

        try {
            CustomUserDetails logUser = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            return logUser.getUser();
        } catch (Exception e) {
            throw e;
        }
    }
}
