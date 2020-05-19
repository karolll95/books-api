package com.karolrinc.books.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
class Book {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    @Length(max = 255, message = "You can enter max 255 characters.")
    private String title;

    @NotEmpty
    @Length(max = 255, message = "You can enter max 255 characters.")
    private String author;

    @NotEmpty
    @IsbnNumber
    private String isbnNumber;

    @NotNull
    private Integer pagesAmount;

    @Min(1)
    @Max(5)
    private Integer rating;

}
