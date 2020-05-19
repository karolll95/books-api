package com.karolrinc.books.api;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
class BookResource extends RepresentationModel<BookResource> {

    private final Book book;

    public BookResource(Book book) {
        this.book = book;
        final long id = book.getId();
        add(
                linkTo(BookController.class).withRel("book"),
                linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel()
           );
    }
}
