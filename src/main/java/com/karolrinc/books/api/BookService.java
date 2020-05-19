package com.karolrinc.books.api;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
class BookService implements BookOperations {

    private final BookRepository bookRepository;

    @Autowired
    BookService(@NonNull final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Book addBook(@NonNull final Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks(@NonNull final Pageable pageable) {
        return bookRepository.findAll(pageable).toList();
    }

    @Override
    public Book getBookById(final long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteBook(final long id) {
        bookRepository.delete(getBookById(id));
    }

    @Override
    @Transactional
    public Book updateBook(final long id, @NonNull final Book book) {
        Book existingBook = getBookById(id);
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbnNumber(book.getIsbnNumber());
        existingBook.setPagesAmount(book.getPagesAmount());
        existingBook.setRating(book.getRating());
        existingBook.setTitle(book.getTitle());
        return addBook(existingBook);
    }
}
