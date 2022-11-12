package com.example.bookservice;

import com.example.bookservice.model.Book;
import com.example.bookservice.model.Category;
import com.example.bookservice.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    private ObjectMapper mapper = new ObjectMapper();


    private Book bookNijntje = new Book("Nijntje", "Dick Bruna", Category.NIJNTJE, false, false, "https://i.postimg.cc/m2VJygQs/Nijntje.jpg", "https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg");
    private Book bookNijntjeInDeSpeeltuin = new Book("Nijntje in de speeltuin", "Dick Bruna", Category.NIJNTJE, false, true, "https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg", "https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg");
    private Book bookBumba = new Book("Samen spelen samen delen", "Gert Verhulst", Category.BUMBA, false, false, "https://i.postimg.cc/rww6Br8s/Bumbasamenspelensamendelen.jpg", "https://i.postimg.cc/L8Qd28XT/Bumbasamenspelensamendelenachterkant.jpg");

    private Book bookToBeDeleted = new Book("Dribbel naar het ziekenhuis", "Eric Hill", Category.DRIBBEL, false, false, "https://i.postimg.cc/W3rxGm7W/Dribbelnaarhetziekenhuis.jpg", "https://i.postimg.cc/43jrH2pH/Dribbelnaarhetziekenhuisachterkant.jpg");


    @BeforeEach
    public void beforeAllTests() {
        bookRepository.deleteAll();
        bookRepository.save(bookNijntje);
        bookRepository.save(bookNijntjeInDeSpeeltuin);
        bookRepository.save(bookBumba);
        bookRepository.save(bookToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        bookRepository.deleteAll();
    }


    @Test
    public void givenTitle_whenGetBookByTitle_thenReturnJsonBook() throws Exception {
        mockMvc.perform(get("/books/title/{title}", "Nijntje in de speeltuin"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Nijntje in de speeltuin")))
                .andExpect(jsonPath("$.author", is("Dick Bruna")))
                .andExpect(jsonPath("$.category", is("NIJNTJE")))
                .andExpect(jsonPath("$.favorite", is(false)))
                .andExpect(jsonPath("$.available", is(true)))
                .andExpect(jsonPath("$.coverImageUrl", is("https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg")))
                .andExpect(jsonPath("$.backCoverImageUrl", is("https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg")));
    }

    @Test
    public void givenCategory_whenGetBooksByCategory_thenReturnJsonBook() throws Exception {
        mockMvc.perform(get("/books/category/{cateorgy}", "NIJNTJE"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Nijntje")))
                .andExpect(jsonPath("$[0].author", is("Dick Bruna")))
                .andExpect(jsonPath("$[0].category", is("NIJNTJE")))
                .andExpect(jsonPath("$[0].favorite", is(false)))
                .andExpect(jsonPath("$[0].available", is(false)))
                .andExpect(jsonPath("$[0].coverImageUrl", is("https://i.postimg.cc/m2VJygQs/Nijntje.jpg")))
                .andExpect(jsonPath("$[0].backCoverImageUrl", is("https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg")))
                .andExpect(jsonPath("$[1].title", is("Nijntje in de speeltuin")))
                .andExpect(jsonPath("$[1].author", is("Dick Bruna")))
                .andExpect(jsonPath("$[1].category", is("NIJNTJE")))
                .andExpect(jsonPath("$[1].favorite", is(false)))
                .andExpect(jsonPath("$[1].available", is(true)))
                .andExpect(jsonPath("$[1].coverImageUrl", is("https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg")))
                .andExpect(jsonPath("$[1].backCoverImageUrl", is("https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg")));
    }

    @Test
    public void whenGetBooks_thenReturnJsonBook() throws Exception {
        mockMvc.perform(get("/books/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].title", is("Nijntje")))
                .andExpect(jsonPath("$[0].author", is("Dick Bruna")))
                .andExpect(jsonPath("$[0].category", is("NIJNTJE")))
                .andExpect(jsonPath("$[0].favorite", is(false)))
                .andExpect(jsonPath("$[0].available", is(false)))
                .andExpect(jsonPath("$[0].coverImageUrl", is("https://i.postimg.cc/m2VJygQs/Nijntje.jpg")))
                .andExpect(jsonPath("$[0].backCoverImageUrl", is("https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg")))
                .andExpect(jsonPath("$[1].title", is("Nijntje in de speeltuin")))
                .andExpect(jsonPath("$[1].author", is("Dick Bruna")))
                .andExpect(jsonPath("$[1].category", is("NIJNTJE")))
                .andExpect(jsonPath("$[1].favorite", is(false)))
                .andExpect(jsonPath("$[1].available", is(true)))
                .andExpect(jsonPath("$[1].coverImageUrl", is("https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg")))
                .andExpect(jsonPath("$[1].backCoverImageUrl", is("https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg")))
                .andExpect(jsonPath("$[2].title", is("Samen spelen samen delen")))
                .andExpect(jsonPath("$[2].author", is("Gert Verhulst")))
                .andExpect(jsonPath("$[2].category", is("BUMBA")))
                .andExpect(jsonPath("$[2].favorite", is(false)))
                .andExpect(jsonPath("$[2].available", is(false)))
                .andExpect(jsonPath("$[2].coverImageUrl", is("https://i.postimg.cc/rww6Br8s/Bumbasamenspelensamendelen.jpg")))
                .andExpect(jsonPath("$[2].backCoverImageUrl", is("https://i.postimg.cc/L8Qd28XT/Bumbasamenspelensamendelenachterkant.jpg")))
                .andExpect(jsonPath("$[3].title", is("Dribbel naar het ziekenhuis")))
                .andExpect(jsonPath("$[3].author", is("Eric Hill")))
                .andExpect(jsonPath("$[3].category", is("DRIBBEL")))
                .andExpect(jsonPath("$[3].favorite", is(false)))
                .andExpect(jsonPath("$[3].available", is(false)))
                .andExpect(jsonPath("$[3].coverImageUrl", is( "https://i.postimg.cc/W3rxGm7W/Dribbelnaarhetziekenhuis.jpg")))
                .andExpect(jsonPath("$[3].backCoverImageUrl", is("https://i.postimg.cc/43jrH2pH/Dribbelnaarhetziekenhuisachterkant.jpg")))
        ;
    }

    @Test
    public void whenPostBook_thenReturnJsonReview() throws Exception {
        Book bookBoeDoetDeKoe = new Book("Boe doet de koe", "Gert Verhulst", Category.BUMBA, false, false, "https://i.postimg.cc/bJBdnh3H/Bumbaboedoetdekoe.jpg", "https://i.postimg.cc/W4dB3cqP/Bumbaboedoetdekoeachterkant.jpg");

        mockMvc.perform(post("/books")
                        .content(mapper.writeValueAsString(bookBoeDoetDeKoe))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Boe doet de koe")))
                .andExpect(jsonPath("$.author", is("Gert Verhulst")))
                .andExpect(jsonPath("$.category", is("BUMBA")))
                .andExpect(jsonPath("$.favorite", is(false)))
                .andExpect(jsonPath("$.available", is(false)))
                .andExpect(jsonPath("$.coverImageUrl", is("https://i.postimg.cc/bJBdnh3H/Bumbaboedoetdekoe.jpg")))
                .andExpect(jsonPath("$.backCoverImageUrl", is("https://i.postimg.cc/W4dB3cqP/Bumbaboedoetdekoeachterkant.jpg")))
        ;
    }

    @Test
    public void givenBook_whenPutBook_thenReturnJsonReview() throws Exception {

        Book updatedBook = new Book("Samen spelen samen delen", "Gert Verhulst", Category.BUMBA, true, true, "https://i.postimg.cc/rww6Br8s/Bumbasamenspelensamendelen.jpg", "https://i.postimg.cc/L8Qd28XT/Bumbasamenspelensamendelenachterkant.jpg");

        mockMvc.perform(put("/books")
                        .content(mapper.writeValueAsString(updatedBook))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Samen spelen samen delen")))
                .andExpect(jsonPath("$.author", is("Gert Verhulst")))
                .andExpect(jsonPath("$.category", is("BUMBA")))
                .andExpect(jsonPath("$.favorite", is(true)))
                .andExpect(jsonPath("$.available", is(true)))
                .andExpect(jsonPath("$.coverImageUrl", is("https://i.postimg.cc/rww6Br8s/Bumbasamenspelensamendelen.jpg")))
                .andExpect(jsonPath("$.backCoverImageUrl", is("https://i.postimg.cc/L8Qd28XT/Bumbasamenspelensamendelenachterkant.jpg")));
    }

    @Test
    public void givenBook_whenDeleteBook_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/books/{bookTitle}", "Dribbel naar het ziekenhuis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoBook_whenDeleteBook_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/books/{bookTitle}", "Titel die niet bestaat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}


