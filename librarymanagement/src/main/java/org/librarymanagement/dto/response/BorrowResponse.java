package org.librarymanagement.dto.response;

import java.util.Map;

public record BorrowResponse(
        String username,
        Map<String,Integer> lists,
        Integer quantity
) {
}
