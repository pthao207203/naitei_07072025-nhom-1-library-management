package org.librarymanagement.repository;

import org.librarymanagement.entity.BorrowRequest;
import org.librarymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Integer> {
    List<BorrowRequest> findBorrowRequestByUser(User user);
}
