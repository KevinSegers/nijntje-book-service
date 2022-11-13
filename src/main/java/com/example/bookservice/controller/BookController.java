package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.model.Category;
import com.example.bookservice.repository.BookRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void fillDB() {
        String gert = "Gert Verhulst";
        String bruna = "Dick Bruna";
        String hill = "Eric Hill";

        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Nijntje in de speeltuin", bruna, Category.NIJNTJE, true, true, "https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg", "https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg"));
            bookRepository.save(new Book("Nijntje", bruna, Category.NIJNTJE, false, false, "https://i.postimg.cc/m2VJygQs/Nijntje.jpg", "https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg"));
            bookRepository.save(new Book("Nijntje in de dierentuin", bruna, Category.NIJNTJE, false, false, "https://i.postimg.cc/Gm5f6g7g/Nijntjeindedierentuin.jpg", "https://i.postimg.cc/65BFZv4c/Nijntjeindedierentuinachterkant.jpg"));
            bookRepository.save(new Book("Samen spelen samen delen", gert, Category.BUMBA, false, false, "https://i.postimg.cc/rww6Br8s/Bumbasamenspelensamendelen.jpg", "https://i.postimg.cc/L8Qd28XT/Bumbasamenspelensamendelenachterkant.jpg"));
            bookRepository.save(new Book("Ik ruim op dat is top", gert, Category.BUMBA, false, false, "https://i.postimg.cc/bN8fR5WQ/Bumbaikruimopdatistop.jpg", "https://i.postimg.cc/dtBPNBcg/Bumbaikruimopdatistopachterkant.jpg"));
            bookRepository.save(new Book("Boe doet de koe", gert, Category.BUMBA, false, false, "https://i.postimg.cc/bJBdnh3H/Bumbaboedoetdekoe.jpg", "https://i.postimg.cc/W4dB3cqP/Bumbaboedoetdekoeachterkant.jpg"));
            bookRepository.save(new Book("Dribbel naar de boerderij", hill, Category.DRIBBEL, false, false, "https://i.postimg.cc/MZ7hXDHX/Dribbelnaardeboerderij.jpg", "https://i.postimg.cc/d3Yp47Hq/Dribbelnaardeboerderijachterkant.jpg"));
            bookRepository.save(new Book("Dribbel naar de bij opa en oma", hill, Category.DRIBBEL, false, false, "https://i.postimg.cc/QNcr1D48/Dribbelbijopaenoma.jpg", "https://i.postimg.cc/qqcVTchh/dribbelbijopaenomaachterkant.jpg"));
            bookRepository.save(new Book("Dribbel naar het ziekenhuis", hill, Category.DRIBBEL, false, false, "https://i.postimg.cc/W3rxGm7W/Dribbelnaarhetziekenhuis.jpg", "https://i.postimg.cc/43jrH2pH/Dribbelnaarhetziekenhuisachterkant.jpg"));

            System.out.println("Books added to database");
        }
    }

    @GetMapping("/books/title/{title}")
    public Book getItemByName(@PathVariable String title) {
        return bookRepository.findBookByTitle(title);
    }

    @GetMapping("/books/category/{category}")
    public List<Book> getBookByCategory(@PathVariable Category category) {
        return bookRepository.findBooksByCategory(category);
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }


    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody @NotNull Book updatedBook) {
        Book retrievedBook = bookRepository.findBookByTitle(updatedBook.getTitle());
        if (retrievedBook.isFavorite() != updatedBook.isFavorite()) {
            retrievedBook.setFavorite(updatedBook.isFavorite());
        }
        if (retrievedBook.isAvailable() != updatedBook.isAvailable()) {
            retrievedBook.setAvailable(updatedBook.isAvailable());
        }
        if (!Objects.equals(retrievedBook.getAuthor(), updatedBook.getAuthor())) {
            retrievedBook.setAuthor(updatedBook.getAuthor());
        }
        if (!Objects.equals(retrievedBook.getTitle(), updatedBook.getTitle())) {
            retrievedBook.setTitle(updatedBook.getTitle());
        }
        if (!Objects.equals(retrievedBook.getCoverImageUrl(), updatedBook.getCoverImageUrl())) {
            retrievedBook.setCoverImageUrl(updatedBook.getCoverImageUrl());
        }
        if (!Objects.equals(retrievedBook.getBackCoverImageUrl(), updatedBook.getBackCoverImageUrl())) {
            retrievedBook.setBackCoverImageUrl(updatedBook.getBackCoverImageUrl());
        }
        bookRepository.save(retrievedBook);
        return retrievedBook;
    }

    @DeleteMapping("/books/{bookTitle}")
    public ResponseEntity<Book> deleteBook(@PathVariable String bookTitle) {
        Book book = bookRepository.findBookByTitle(bookTitle);
        if (book != null) {
            bookRepository.delete(book);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
