package org.librarymanagement.repository;

import org.librarymanagement.entity.Book;
import org.librarymanagement.entity.BookVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookVersionRepository extends JpaRepository<BookVersion,Integer> {
}
