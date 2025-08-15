package org.librarymanagement.service.impl;

import org.librarymanagement.dto.response.BookDetailResponse;
import org.librarymanagement.dto.response.ReviewResponse;
import org.librarymanagement.dto.response.UserResponse;
import org.librarymanagement.entity.Book;
import org.librarymanagement.entity.BookAuthor;
import org.librarymanagement.entity.Publisher;
import org.librarymanagement.entity.Review;
import org.librarymanagement.exception.NotFoundException;
import org.librarymanagement.repository.BookRepository;
import org.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findBookBySlug(String slug) {
        return bookRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sách với slug: " + slug));
    }

    @Override
    public BookDetailResponse  createBookDetailResponseBySlug(String slug) {
        Book book = findBookBySlug(slug);

        Set<BookAuthor> bookAuthors = book.getBookAuthors();

        List<String> authorNames = new ArrayList<>();

        authorNames = bookAuthors.stream()
                .map(bookAuthor -> bookAuthor.getAuthor().getName())
                .collect(Collectors.toList());

        Publisher publisher = book.getPublisher();

        String publisherName = (publisher != null) ? publisher.getName() : null;

        Set<Review>  reviews = book.getReviews();

        Set<ReviewResponse> reviewResponses = convertReviewsToDtos(reviews);

        BookDetailResponse bookDetailResponse = new BookDetailResponse(
                book.getId(),
                book.getImage(),
                book.getTitle(),
                book.getTotalCurrent(),
                book.getTotalQuantity(),
                book.getDescription(),
                book.getPublishedDay(),
                authorNames,
                publisherName,
                reviewResponses
        );

        return  bookDetailResponse;
    }

    private Set<ReviewResponse> convertReviewsToDtos(Set<Review> reviews) {
        if (reviews == null) {
            return Collections.emptySet();
        }

        Set<ReviewResponse> reviewResponses = new HashSet<>();

        reviewResponses = reviews.stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getComment(),
                        review.getStar(),
                        review.getCreatedAt(),
                        new UserResponse(
                                review.getUser().getId(),
                                review.getUser().getName(),
                                review.getUser().getUsername()
                        )
                ))
                .collect(Collectors.toSet());

        return reviewResponses;
    }
}
