package com.karolrinc.books.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookApiApplication.class)
class BookControllerIntegrationTest {

    private final BookController bookController;

    @MockBean
    private BookService bookService;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    BookControllerIntegrationTest(BookController bookController) {this.bookController = bookController;}

    @Test
    void addBook_success() {
        final Book book = getBook(null);
        final Book savedBook = getBook(1L);
        when(bookService.addBook(book)).thenReturn(savedBook);

        ResponseEntity<?> result = bookController.addBook(book, bindingResult);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(savedBook, ((BookResource) result.getBody()).getBook());
    }

    @Test
    void addBook_validationError() {
        final Book book = getBook(null);
        final List<ObjectError> errors = singletonList(new ObjectError("name", "defaultMsg"));
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(errors);

        ResponseEntity<?> result = bookController.addBook(book, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(errors, result.getBody());
    }

    @Test
    void getBookById_success() {
        final long bookId = 1L;
        when(bookService.getBookById(bookId)).thenReturn(getBook(bookId));

        ResponseEntity<?> result = bookController.getBookById(bookId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        Book book = ((BookResource) result.getBody()).getBook();
        assertEquals(getBook(bookId), book);
    }

    @Test
    @SuppressWarnings("unchecked")
    void getBooks_success() {
        final int size = 10;
        final int page = 0;
        when(bookService.getBooks(PageRequest.of(0, 10)))
                .thenReturn(asList(getBook(1L), getBook(2L), getBook(3L)));

        ResponseEntity<?> result = bookController.getBooks(size, page);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        CollectionModel<BookResource> body = (CollectionModel<BookResource>) result.getBody();
        assertFalse(body.getContent().isEmpty());
        assertEquals(3, body.getContent().size());
        assertTrue(body.getLink("self").isPresent());
    }

    @Test
    void deleteBook_success() {
        final long bookId = 1L;

        ResponseEntity<?> result = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void updateBook_success() {
        final long bookId = 1L;
        Book bookToUpdate = getBook(1L);
        bookToUpdate.setAuthor("Updated author");
        when(bookService.updateBook(bookId, bookToUpdate)).thenReturn(bookToUpdate);

        ResponseEntity<?> result = bookController.updateBook(bookId, bookToUpdate, bindingResult);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        Book book = ((BookResource) result.getBody()).getBook();
        assertEquals(bookToUpdate, book);
    }

    @Test
    void updateBook_validationErrors() {
        final long bookId = 1L;
        Book bookToUpdate = getBook(1L);
        bookToUpdate.setAuthor("Updated author");
        List<ObjectError> errors = singletonList(new ObjectError("name", "defaultMsg"));
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(errors);

        ResponseEntity<?> result = bookController.updateBook(bookId, bookToUpdate, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(errors, result.getBody());
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