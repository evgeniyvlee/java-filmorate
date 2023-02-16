package ru.yandex.practicum.filmorate;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.UserController;
import java.time.LocalDate;
import com.google.gson.Gson;
import ru.yandex.practicum.filmorate.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserController controller;

    private User user;

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
        user = new User();
        user.setEmail("johnnylee@yandex.ru");
        user.setLogin("johnnylee");
        user.setName("Evgeniy Lee");
        user.setBirthday(LocalDate.of(1987, 2, 7));
    }

    @Test
    void validateNullEmail() throws Exception {
        user.setEmail(null);
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateBlankEmail() throws Exception {
        user.setEmail("");
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateInvalidFormatEmail() throws Exception {
        user.setEmail("johnnylee_yandexru");
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateNullLogin() throws Exception {
        user.setLogin(null);
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateBlankLogin() throws Exception {
        user.setLogin("");
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateLoginWithSpaces() throws Exception {
        user.setLogin("johnny lee");
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateNullBirthday() throws Exception {
        user.setBirthday(null);
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void validateBirthdayInFuture() throws Exception {
        user.setBirthday(LocalDate.of(2087, 2, 7));
        String result = mockMvc.perform(
                        post("/users")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}