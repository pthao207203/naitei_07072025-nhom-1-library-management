package org.librarymanagement.controller.api;

import jakarta.validation.Valid;
import org.librarymanagement.constant.ApiEndpoints;
import org.librarymanagement.dto.request.RegisterUserDto;
import org.librarymanagement.dto.response.LoginResponseDto;
import org.librarymanagement.dto.request.LoginUserDto;
import org.librarymanagement.dto.response.ResponseObject;
import org.librarymanagement.service.AuthService;
import org.librarymanagement.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userAuthController")
@RequestMapping(ApiEndpoints.USER_AUTH)
public class AuthController {
    private final AuthService loginService;
    private final RegisterService registerService;

    public AuthController(AuthService loginService , RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        LoginResponseDto response = loginService.login(loginUserDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto){
        ResponseObject responseObject =  registerService.registerUser(registerUserDto);
        return ResponseEntity.status(responseObject.status())
                .body(responseObject);
    }
}
