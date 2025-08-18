package org.librarymanagement.service.impl;

import org.librarymanagement.dto.response.BookDetailResponse;
import org.librarymanagement.dto.response.ReviewResponse;
import org.librarymanagement.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.librarymanagement.dto.response.BookDto;
import org.librarymanagement.dto.response.BookFlatDto;
import org.librarymanagement.entity.Book;
import org.librarymanagement.entity.BookAuthor;
import org.librarymanagement.entity.Publisher;
import org.librarymanagement.entity.Review;
import org.librarymanagement.exception.NotFoundException;
import org.librarymanagement.repository.BookRepository;
import org.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookDto> findAllBooksWithFilter(Pageable pageable) {
        Page<BookFlatDto> rawBooks = bookRepository.findAllBooksFlat(pageable);

        // Step 1: Group theo sách (title + publisher) và gom tác giả
        Map<String, Set<String>> authorsMap = rawBooks.getContent().stream()
                .collect(Collectors.groupingBy(
                        dto -> dto.bookTitle() + "|" + dto.bookPublisher(), // key duy nhất cho 1 sách
                        LinkedHashMap::new,
                        Collectors.mapping(BookFlatDto::bookAuthor, Collectors.toSet())
                ));

        // Step 2: Map sang BookDto
        List<BookDto> dtos = rawBooks.getContent().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                dto -> dto.bookTitle() + "|" + dto.bookPublisher(),
                                dto -> new BookDto(
                                        dto.bookImage(),
                                        dto.bookTitle(),
                                        dto.bookDescription(),
                                        authorsMap.get(dto.bookTitle() + "|" + dto.bookPublisher()), // Set tác giả
                                        dto.bookPublisher()
                                ),
                                (existing, newDto) -> existing, // nếu trùng key, giữ existing
                                LinkedHashMap::new
                        ),
                        m -> new ArrayList<>(m.values())
                ));

        // Step 3: Trả về Page<BookDto>
        return new PageImpl<>(dtos, pageable, rawBooks.getTotalElements());
    }

    @Override
    public Book findBookBySlug(String slug) {
        return bookRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sách với slug: " + slug));
    }
    @Override
    public BookDetailResponse createBookDetailResponseBySlug(String slug) {
        Book book = findBookBySlug(slug);

        Set<BookAuthor> bookAuthors = book.getBookAuthors();

        List<String> authorNames = new ArrayList<>();

        authorNames = bookAuthors.stream()
                .map(bookAuthor -> bookAuthor.getAuthor().getName())
                .collect(Collectors.toList());

        Publisher publisher = book.getPublisher();

        String publisherName = (publisher != null) ? publisher.getName() : null;

        Set<Review> reviews = book.getReviews();

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

        return bookDetailResponse;
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
