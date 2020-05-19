# Books API

Books API is designed to provide You with CRUD operations for books.

## Installation

The fastest way to run the application is to import this project to your IDE and run BookApiApplication.class.

With default configuration You need to have MySQL database named booksdb running on localhost with MySQL credentials: root:root.
By default server will start at http://localhost:9191
You can customize these settings by changing the application.properties file.



## Usage

### Authentication
To use the API, You need to authenticate every requst with JWT token.
To gain token you need to execute POST request:

```
POST /api/authenticate
```
and provide user credentials in the body. These are the default values, configurable in application.properties file:

```
{
	"username":"brightInventions",
	"password":"password"
}
```
Token needs to be added to every request in the Authorization header like:

```
Authorization: Bearer token
```

### Endpoints

#### Add new book
```
POST /api/books
```
Model:
```
{
	"title": String,
	"author": String,
	"isbnNumber": String,
	"pagesAmount": Integer,
	"rating": Integer
}
```

Sample body:
```
{
	"title": "Tytuł",
	"author": "Autor Nieznany",
	"isbnNumber": "ISBN-10 0-596-52068-9",
	"pagesAmount": 100,
	"rating": 3
}
```

Every field is required. Title and author can contain 255 characters max. Rating should be between 1 and 5. IsbnNumber should be in valid ISBN-10 or ISBN-13 format.

#### Get single book
```
GET /api/books/id
```
Where as id you need to enter book ID in the system.
Result:

```
{
    "book": {
        "id": Integer,
        "title": String,
        "author": String,
        "isbnNumber": String,
        "pagesAmount": Integer,
        "rating": Integer
    },
    "_links": {
        "book": {
            "href": "http://localhost:9191/api/books"
        },
        "self": {
            "href": "http://localhost:9191/api/books/id"
        }
    }
}
```

#### Get books
```
GET /api/books
```
You can also add request parameters which would define pagination:
```
GET /api/books?size=10&page=0
```
Sample result:
```
{
    "_embedded": {
        "bookResourceList": [
            {
                "book": {
                    "id": 1,
                    "title": "Tytuł",
                    "author": "Autor Nieznany",
                    "isbnNumber": "ISBN 978-0-596-52068-7",
                    "pagesAmount": 100,
                    "rating": 3
                },
                "_links": {
                    "book": {
                        "href": "http://localhost:9191/api/books"
                    },
                    "self": {
                        "href": "http://localhost:9191/api/books/1"
                    }
                }
            },
            {
                "book": {
                    "id": 2,
                    "title": "Tytuł",
                    "author": "Autor Nieznany",
                    "isbnNumber": "ISBN-13: 978-0-596-52068-7",
                    "pagesAmount": 100,
                    "rating": 3
                },
                "_links": {
                    "book": {
                        "href": "http://localhost:9191/api/books"
                    },
                    "self": {
                        "href": "http://localhost:9191/api/books/2"
                    }
                }
            },
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:9191/api/books"
        }
    }
}
```

#### Update book
```
PUT /api/books/id
```
Where as id you need to enter book ID in the system. As a body You need to define Book with valid data:
```
{
	"title": "Tytuł",
	"author": "Autor Nieznany",
	"isbnNumber": "ISBN-10 0-596-52068-9",
	"pagesAmount": 100,
	"rating": 3
}
```

#### Delete book
```
DELETE /api/books/id
```
Where as id you need to enter book ID in the system.
