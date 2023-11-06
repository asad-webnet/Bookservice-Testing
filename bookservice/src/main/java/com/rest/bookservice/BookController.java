package com.rest.bookservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private Map<String, Book> bookMap = new HashMap<>();

    public BookController() {
        Book book1 = new Book("1234","asad","Harry",12.4);
        bookMap.put(book1.getIsbn(),book1);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String author) {
        var books = bookMap.values();

        if(author!=null) {
            books = bookMap.values().stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                    .collect(Collectors.toList());
        } else {
            books = bookMap.values();
        }
        return new ResponseEntity<>(new ArrayList<>(bookMap.values()), HttpStatus.OK);

    }
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable String isbn) {
        Book book = bookMap.get(isbn);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (!bookMap.containsKey(book.getIsbn())) {
            bookMap.put(book.getIsbn(), book);
            return new ResponseEntity<>(bookMap.get(book.getIsbn()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Book with this ISBN already exists", HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateBook(@RequestBody Book book) {
        if (bookMap.containsKey(book.getIsbn())) {
            bookMap.put(book.getIsbn(), book);
            return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        if (bookMap.containsKey(isbn)) {
            bookMap.remove(isbn);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

}