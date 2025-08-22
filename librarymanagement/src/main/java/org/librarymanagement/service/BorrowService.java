package org.librarymanagement.service;

import org.librarymanagement.dto.response.ResponseObject;
import org.librarymanagement.entity.User;

import java.util.Map;

public interface BorrowService {
    ResponseObject borrowBook(Map<Integer, Integer> bookBorrows, User user);

}
