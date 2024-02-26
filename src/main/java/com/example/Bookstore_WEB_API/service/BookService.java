package com.example.Bookstore_WEB_API.service;

import com.example.Bookstore_WEB_API.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book saveBook(Book book);

    Book getBookById(Long id);

    void deleteBookById(Long id);

    Page<Book> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection);
}
