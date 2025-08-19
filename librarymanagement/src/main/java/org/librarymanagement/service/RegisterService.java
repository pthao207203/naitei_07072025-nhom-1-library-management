package org.librarymanagement.service;

import org.librarymanagement.dto.request.RegisterUserDto;
import org.librarymanagement.dto.response.ResponseObject;
import org.librarymanagement.entity.User;

public interface RegisterService {
    ResponseObject registerUser(RegisterUserDto registerUserDto);
    void saveUser(User user);
}
