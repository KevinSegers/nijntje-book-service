package com.example.bookservice;

import com.example.bookservice.model.Book;
import com.example.bookservice.model.Category;
import com.example.bookservice.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookservice.model.Category.NIJNTJE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    private final ObjectMapper mapper = new ObjectMapper();



    @Test
    void givenBook_whenGetBookByTitle_thenReturnJsonBook() throws Exception {
        Book bookNijntje = new Book("Nijntje", "Dick Bruna", NIJNTJE, false, false, "https://i.postimg.cc/m2VJygQs/Nijntje.jpg", "https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg");

        given(bookRepository.findBookByTitle("Nijntje")).willReturn(bookNijntje);

        mockMvc.perform(get("/books/title/{title}", "Nijntje"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Nijntje")))
                .andExpect(jsonPath("$.author", is("Dick Bruna")))
                .andExpect(jsonPath("$.category", is("NIJNTJE")))
                .andExpect(jsonPath("$.favorite", is(false)))
                .andExpect(jsonPath("$.available", is(false)))
                .andExpect(jsonPath("$.coverImageUrl", is("https://i.postimg.cc/m2VJygQs/Nijntje.jpg")))
                .andExpect(jsonPath("$.backCoverImageUrl", is("https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg")));
    }

    @Test
    void givenBook_whenGetBooks_thenReturnJsonBook() throws Exception {
        Book bookNijntjeInDeSpeeltuin = new Book("Nijntje in de speeltuin", "Dick Bruna", NIJNTJE, false, true, "https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg", "https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg");
        Book bookNijntje = new Book("Nijntje", "Dick Bruna", NIJNTJE, false, false, "https://i.postimg.cc/m2VJygQs/Nijntje.jpg", "https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg");

        List<Book> bookList = new ArrayList<>();
        bookList.add(bookNijntje);
        bookList.add(bookNijntjeInDeSpeeltuin);

        given(bookRepository.findAll()).willReturn(bookList);

        mockMvc.perform(get("/books"))
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
    void givenBook_whenGetBooksByCategory_thenReturnJsonBook() throws Exception {
        Book bookNijntjeInDeSpeeltuin = new Book("Nijntje in de speeltuin", "Dick Bruna", NIJNTJE, false, true, "https://i.postimg.cc/RFrDZ6zc/Nijntjeindespeeltuin.jpg", "https://i.postimg.cc/8cm3319L/Nijntjeindespeeltuinachterkant.jpg");
        Book bookNijntje = new Book("Nijntje", "Dick Bruna", NIJNTJE, false, false, "https://i.postimg.cc/m2VJygQs/Nijntje.jpg", "https://i.postimg.cc/FHRqWXZJ/Nijntjeachterkant.jpg");

        List<Book> bookList = new ArrayList<>();
        bookList.add(bookNijntje);
        bookList.add(bookNijntjeInDeSpeeltuin);

        given(bookRepository.findBooksByCategory(NIJNTJE)).willReturn(bookList);

        mockMvc.perform(get("/books/category/{category}", "NIJNTJE"))
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
    void whenPostBook_thenReturnJsonReview() throws Exception {
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
    void givenBook_whenPutBook_thenReturnJsonReview() throws Exception {
        Book bookBumba = new Book("Samen spelen samen delen", "Gert Verhulst", Category.BUMBA, false, false, "https://i.postimg.cc/rww6Br8s/Bumbasamenspelensamendelen.jpg", "https://i.postimg.cc/L8Qd28XT/Bumbasamenspelensamendelenachterkant.jpg");

        given(bookRepository.findBookByTitle("Samen spelen samen delen")).willReturn(bookBumba);

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
    void givenBook_whenDeleteBook_thenStatusOk() throws Exception {
        Book bookToBeDeleted = new Book("Dribbel naar het ziekenhuis", "Eric Hill", Category.DRIBBEL, false, false, "https://i.postimg.cc/W3rxGm7W/Dribbelnaarhetziekenhuis.jpg", "https://i.postimg.cc/43jrH2pH/Dribbelnaarhetziekenhuisachterkant.jpg");

        given(bookRepository.findBookByTitle("Dribbel naar het ziekenhuis")).willReturn(bookToBeDeleted);


        mockMvc.perform(delete("/books/{bookTitle}", "Dribbel naar het ziekenhuis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenNoBook_whenDeleteBook_thenStatusNotFound() throws Exception {
        given(bookRepository.findBookByTitle("Titel die niet bestaat")).willReturn(null);


        mockMvc.perform(delete("/books/{bookTitle}", "Titel die niet bestaat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
