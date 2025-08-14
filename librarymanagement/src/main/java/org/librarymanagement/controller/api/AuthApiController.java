package org.librarymanagement.controller.api;

import jakarta.validation.Valid;
import org.librarymanagement.constant.ApiEndpoints;
import org.librarymanagement.dto.request.RegisterUserDto;
import org.librarymanagement.dto.response.ResponseObject;
import org.librarymanagement.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping(ApiEndpoints.USER_AUTH)
public class AuthApiController {
    private final RegisterService userService;
    private final MessageSource messageSource;

    @Autowired
    public AuthApiController(RegisterService userService,  MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto){
        userService.registerUser(registerUserDto);
        String successMessage = messageSource.getMessage("user.registration.success", null, Locale.getDefault());
        return ResponseEntity.ok( new ResponseObject(
                        successMessage,
                        HttpStatus.OK.value(),
                        registerUserDto
        ));
    }
}
