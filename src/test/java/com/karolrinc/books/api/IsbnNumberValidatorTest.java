package com.karolrinc.books.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsbnNumberValidatorTest {
    private static final String ISBN_13 = "ISBN-13: 978-0-596-52068-7";
    private static final String ISBN_10 = "ISBN-10: 0-596-52068-9";
    private static final String ISBN_INVALID = "ISBN-123: 40-596-52068-9";

    private final IsbnNumberValidator isbnNumberValidator;

    public IsbnNumberValidatorTest() {
        this.isbnNumberValidator = new IsbnNumberValidator();
    }

    @Test
    void isValid_isbn13_valid() {
        assertTrue(isbnNumberValidator.isValid(ISBN_13, null));
    }

    @Test
    void isValid_isbn10_valid() {
        assertTrue(isbnNumberValidator.isValid(ISBN_10, null));
    }

    @Test
    void isValid_invalidIsbn_invalid() {
        assertFalse(isbnNumberValidator.isValid(ISBN_INVALID, null));
    }

    @Test
    void initialize_success() {
        isbnNumberValidator.initialize(null);
    }
}