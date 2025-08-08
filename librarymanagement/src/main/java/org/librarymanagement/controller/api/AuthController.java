package org.librarymanagement.controller.api;

import jakarta.validation.Valid;
import org.librarymanagement.constant.ApiEndpoints;
import org.librarymanagement.dto.response.LoginResponseDto;
import org.librarymanagement.dto.request.LoginUserDto;
import org.librarymanagement.service.impl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userAuthController")
@RequestMapping(ApiEndpoints.USER_AUTH)
public class AuthController {
    private final AuthServiceImpl loginService;

    public AuthController(AuthServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        LoginResponseDto response = loginService.login(loginUserDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
