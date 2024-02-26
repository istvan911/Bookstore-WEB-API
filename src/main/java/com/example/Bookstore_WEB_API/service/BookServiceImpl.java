package com.example.Bookstore_WEB_API.service;

import com.example.Bookstore_WEB_API.exception.BookNotFoundException;
import com.example.Bookstore_WEB_API.model.Book;
import com.example.Bookstore_WEB_API.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getId() == null) {
            if (isTitleAlreadyExists(book.getTitle()))
                throw new IllegalArgumentException("Ez a könyv már regisztrálva van!");
            this.bookRepository.save(book);
        } else {
            Book existingBook = this.bookRepository.findById(book.getId()).orElseThrow(
                    () -> new BookNotFoundException("A könyv nem található!")
            );

            if (!book.getTitle().equals(existingBook.getTitle())) {
                if (isTitleAlreadyExists(book.getTitle())) {
                    throw new IllegalArgumentException("Ez a cím már regisztrálva van!");
                }
            }
            this.bookRepository.save(book);
        }

        return book;
    }

    private boolean isTitleAlreadyExists(String email) {
        return bookRepository.findByTitle(email).isPresent();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Az ID=" + id + " könyv nem található.";
            return new BookNotFoundException(errorMessage);
        });
    }

    @Override
    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.bookRepository.findAll(pageable);
    }
}
