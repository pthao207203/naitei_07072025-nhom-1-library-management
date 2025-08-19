package org.librarymanagement.service;

import org.librarymanagement.dto.response.BookDetailResponse;
import org.springframework.data.domain.Page;
import org.librarymanagement.dto.response.BookDto;
import org.springframework.data.domain.Pageable;
import org.librarymanagement.entity.Book;

public interface BookService {
    Page<BookDto> findAllBooksWithFilter(Pageable pageable);
    BookDetailResponse createBookDetailResponseBySlug(String slug);
    Book findBookBySlug(String slug);
}
