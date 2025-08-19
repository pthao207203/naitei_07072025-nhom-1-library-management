package org.librarymanagement.service.impl;

import org.librarymanagement.entity.Author;
import org.librarymanagement.repository.AuthorRepository;
import org.librarymanagement.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findOrCreateAuthor(String authorName) {
        return authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author a = new Author();
                    a.setName(authorName);
                    return authorRepository.save(a);
                });
    }
}
