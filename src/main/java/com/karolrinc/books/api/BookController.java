package com.karolrinc.books.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/api/books", produces = "application/hal+json")
class BookController {

    private final BookService bookService;

    @Autowired
    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBook(@RequestBody @Valid final Book book, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        final Book savedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BookResource(savedBook));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getBookById(@PathVariable final long id) {
        final Book book = bookService.getBookById(id);
        return ResponseEntity.ok(new BookResource(book));
    }

    @GetMapping
    public ResponseEntity<?> getBooks(@RequestParam(required = false, defaultValue = "50") final Integer size,
                                      @RequestParam(required = false, defaultValue = "0") final Integer page) {
        final List<BookResource> books = bookService.getBooks(PageRequest.of(page, size))
                                                    .stream()
                                                    .map(BookResource::new)
                                                    .collect(toList());
        final CollectionModel<BookResource> bookResources = CollectionModel.of(books);
        bookResources.add(Link.of(fromCurrentRequest().build().toUriString(), "self"));

        return ResponseEntity.ok(bookResources);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable final long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateBook(@PathVariable final long id, @RequestBody @Valid final Book book,
                                        final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        final Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(new BookResource(updatedBook));
    }

}
