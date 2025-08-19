package org.librarymanagement.service;

import org.librarymanagement.dto.response.BookDetailResponse;
import org.librarymanagement.dto.response.ReviewResponse;
import org.librarymanagement.entity.Book;
import org.librarymanagement.entity.Review;

import java.util.Set;

public interface BookService {
    public Book findBookBySlug(String slug);
    public BookDetailResponse createBookDetailResponseBySlug(String slug);
}
