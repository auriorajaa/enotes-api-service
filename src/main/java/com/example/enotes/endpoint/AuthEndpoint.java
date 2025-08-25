package com.example.enotes.endpoint;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User Authentication", description = "User authentication related API's")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    @Operation(summary = "Create/register account endpoint")
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception;

    @Operation(summary = "Login endpoint")
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;

}
