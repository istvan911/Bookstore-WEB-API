package com.example.Bookstore_WEB_API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mybooks")
@Getter
@Setter
public class MyBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Hiba a 'Title' mezőben. A mező nem lehet üres.")
    private String title;

    @Column(nullable = false)
    @NotEmpty(message = "Hiba a 'author' mezőben. A mező nem lehet üres.")
    private String author;

    @Column(nullable = false)
    @NotNull(message = "Hiba az 'price' mezőben. A mező nem lehet üres.")
    private Integer price;

    @Column(nullable = false)
    @NotNull(message = "Hiba az 'available' mezőben. A mező nem lehet null.")
    private Boolean available;
}