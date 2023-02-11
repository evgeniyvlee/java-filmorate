package ru.yandex.practicum.filmorate;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import com.google.gson.Gson;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerTest.class)
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmController controller;

    private Film film;

    private static Gson gson;

    @BeforeAll
    static void beforeAll() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @BeforeEach
    void beforeEach() {
        film = new Film();
        film.setName("Casino Royale");
        film.setDescription("Special Agent James Bond embarks on a mission to " +
                                "prevent Le Chiffre, a mob banker, from winning a high" +
                                    " stakes poker game. He is aided by Vesper Lynd, a British Treasury agent.");
        film.setReleaseDate(LocalDate.of(2006, 11, 16));
        film.setDuration(144);
    }

    @Test
    void validateNullName() throws Exception {
        film.setName(null);
        String result = mockMvc.perform(
            post("/films")
                    .content(gson.toJson(film))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateBlankName() throws Exception {
        film.setName("");
        String result = mockMvc.perform(
                        post("/films")
                                .content(gson.toJson(film))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateMaxLengthDescription() throws Exception {
        film.setDescription("Casino Royale is a 2006 spy film, the twenty-first in the Eon Productions " +
                "James Bond series, and the third screen adaptation of Ian Fleming's 1953 novel of the same " +
                "name. Directed by Martin Campbell from a screenplay by Neil Purvis, Robert Wade, and Paul Haggis, " +
                "it stars Daniel Craig in his first appearance as Bond, alongside Eva Green, Mads Mikkelsen, " +
                "Judi Dench, and Jeffrey Wright. In the film, Bond is on assignment to bankrupt terrorist financier " +
                "Le Chiffre (Mikkelsen) in a high-stakes poker game at the Casino Royale in Montenegro.\n" +
                "\n" +
                "Following Die Another Day (2002), Eon decided to reboot the franchise,[4][5] attempting to " +
                "counteract perceived unrealistic elements of previous entries and instead explore a less experienced, " +
                "more vulnerable Bond.[6] Casting involved a widespread search for a new actor to succeed Pierce " +
                "Brosnan as Bond; the choice of Craig, announced in October 2005, proved controversial. " +
                "Principal photography took place in the Bahamas, Italy, the United Kingdom, and the Czech " +
                "Republic, with interior sets built at Pinewood Studios and Barrandov Studios. Casino Royale " +
                "features primarily practical stuntwork as opposed to the computer-generated placements " +
                "seen in other Bond films."
        );
        String result = mockMvc.perform(
                        post("/films")
                                .content(gson.toJson(film))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateReleaseDate() throws Exception {
        film.setReleaseDate(LocalDate.of(1812, 11, 16));
        String result = mockMvc.perform(
                        post("/films")
                                .content(gson.toJson(film))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateDuration() throws Exception {
        film.setDuration(-144);
        String result = mockMvc.perform(
                        post("/films")
                                .content(gson.toJson(film))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

        public static class MockDependencies {
        @Bean
        public MockMvc mvc() {
            return MockMvcBuilders.standaloneSetup(new FilmController()).build();
        }
    }
}
