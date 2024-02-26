package com.example.Bookstore_WEB_API.controller;

import com.example.Bookstore_WEB_API.model.Book;
import com.example.Bookstore_WEB_API.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Validated
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Az Employee lista megjelenítése lapozással
        return findPaginated(1, "id", "desc", model);
    }

    @GetMapping("/showNewBookForm")
    public String showNewBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "new_book";
    }

    // Save book method with validation
    @PostMapping("/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book, Model model) {
        try {
            bookService.saveBook(book);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "new_book";
        }
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "update_book";
    }

    @PostMapping("/updateBook")
    public String updateBook(@Valid @ModelAttribute("book") Book book, Model model) {
        try {
            bookService.saveBook(book);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "update_book";
        }
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        this.bookService.deleteBookById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDirection") String sortDirection,
                                Model model) {
        Integer pageSize = 5;
        Page<Book> page = bookService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Book> bookList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listBooks", bookList);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        return "index";
    }
}
