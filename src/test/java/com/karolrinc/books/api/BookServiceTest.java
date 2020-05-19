package com.karolrinc.books.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    private final BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    BookServiceTest(BookService bookService) {this.bookService = bookService;}

    @Test
    void addBook_success() {
        final Book book = getBook(null);
        when(bookRepository.save(book)).thenReturn(getBook(1L));

        Book result = bookService.addBook(book);

        assertEquals(1, result.getId());
        verify(bookRepository).save(book);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getBooks_success() {
        final PageRequest pageRequest = PageRequest.of(0, 10);
        when(bookRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(asList(getBook(1L), getBook(2L), getBook(3L))));

        List<Book> result = bookService.getBooks(pageRequest);

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        verify(bookRepository).findAll(pageRequest);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getBooks_emptyResult() {
        final PageRequest pageRequest = PageRequest.of(0, 10);
        when(bookRepository.findAll(pageRequest)).thenReturn(Page.empty());

        List<Book> result = bookService.getBooks(pageRequest);

        assertTrue(result.isEmpty());
        verify(bookRepository).findAll(pageRequest);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getBookById_success() {
        final long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(getBook(bookId)));

        Book result = bookService.getBookById(bookId);

        assertEquals(bookId, result.getId());
        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void getBookById_notFound() {
        final long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(bookId));

        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void delete_success() {
        final long bookId = 1L;
        Book book = getBook(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.deleteBook(bookId);

        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(book);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void delete_notFound() {
        final long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(bookId));

        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void update_success() {
        final long bookId = 1L;
        Book book = getBook(bookId);
        Book bookToUpdate = getBook(1L);
        bookToUpdate.setAuthor("Updated author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(bookToUpdate)).thenReturn(bookToUpdate);

        Book result = bookService.updateBook(bookId, bookToUpdate);

        assertEquals("Updated author", result.getAuthor());
        verify(bookRepository).findById(bookId);
        verify(bookRepository).save(bookToUpdate);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void update_notFound() {
        final long bookId = 1L;
        Book bookToUpdate = getBook(1L);
        bookToUpdate.setAuthor("Updated author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(bookId, bookToUpdate));

        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    private Book getBook(final Long id) {
        Book book = new Book();
        book.setTitle("Sample title");
        book.setRating(1);
        book.setPagesAmount(100);
        book.setIsbnNumber("21332143");
        book.setAuthor("Sample author");
        if (nonNull(id)) {
            book.setId(id);
        }
        return book;
    }
}