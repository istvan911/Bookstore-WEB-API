package com.example.Bookstore_WEB_API.repository;

import com.example.Bookstore_WEB_API.model.Book;
import com.example.Bookstore_WEB_API.model.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    Optional<MyBook> findByTitle(String title);
}
