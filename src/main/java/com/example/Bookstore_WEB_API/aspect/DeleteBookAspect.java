package com.example.Bookstore_WEB_API.aspect;

import com.example.Bookstore_WEB_API.exception.BookNotFoundException;
import com.example.Bookstore_WEB_API.model.Book;
import com.example.Bookstore_WEB_API.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "fileLogger")
public class DeleteBookAspect {
    @Autowired
    private BookRepository bookRepository;

    @Before(value = "execution(* com.example.Bookstore_WEB_API.service.BookService.deleteBookById(..))")
    public void beforeDeleteEmployee(JoinPoint joinPoint) {
        log.info("---------- @Before - Könyv törlésének kezdete---------------");
        // Könyv ID-jának megszerzése
        Long id = (Long) joinPoint.getArgs()[0];

        // A könyv objektum megszerzése ID alapján
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Az ID: " + id + ", könyv nem található ")
        );

        log.info("Könyv törlése - " + book.getTitle() + " " + book.getAuthor() + " " + book.getPrice()  + "ID: " + book.getId());
        log.info("---------- @Before - Könyv törlésének vége -----------------");
    }

    @After(value = "execution(* com.example.Bookstore_WEB_API.service.BookService.deleteBookById(..))")
    public void afterDeleteEmployee(JoinPoint joinPoint) {
        log.info("---------- @After - Könyv törlésének kezdete ---------------");
        Long bookId = (Long) joinPoint.getArgs()[0];
        log.info("Az ID: " + bookId + ", könyv törölve lett!");
        log.info("---------- @After - Könyv törlésének vége -----------------");
    }
}
