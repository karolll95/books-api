package com.karolrinc.books.api;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookOperations {
    Book addBook(final Book book);

    List<Book> getBooks(final Pageable pageable);

    Book getBookById(final long id);

    void deleteBook(final long id);

    Book updateBook(final long id, final Book book);
}
