package com.charles.knightonline.controller;

import com.charles.knightonline.KnightOnlineApplication;
import com.charles.knightonline.commons.CommonIntTest;
import com.charles.knightonline.commons.annotations.user.RunWithMockCustomUser;
import com.charles.knightonline.enums.GenderEnum;
import com.charles.knightonline.model.dto.UserDTO;
import com.charles.knightonline.model.dto.UserNameGenderDTO;
import com.charles.knightonline.service.mock.UserMock;
import com.charles.knightonline.utils.SerializationUtils;
import com.charles.knightonline.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWithMockCustomUser
@SpringBootTest(classes = KnightOnlineApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends CommonIntTest {

    private static final String BASE_URL = "/user";

    @Autowired
    private UserMock userMock;

    @BeforeEach
    public void init() {
        UserDTO dto = userMock.getUserMock();
        userMock.createUser(dto);
    }

    @AfterEach
    public void close() {
        userMock.deleteAllUser();
    }

    @Order(1)
    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() throws Exception {
        UserDTO dto = userMock.getUserMock(TestUtils.generateRandomString().concat("@email.com"), TestUtils.generateRandomString());
        this.getMockMvc()
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_success"));
    }

    @Test
    @DisplayName("Should update user")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldUpdateUser() throws Exception {
        UserNameGenderDTO dto = new UserNameGenderDTO();
        dto.setName(TestUtils.generateRandomString());
        dto.setGender(GenderEnum.MALE);

        this.getMockMvc()
                .perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_success"));
    }

    @Test
    @DisplayName("Should get user")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetUser() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, userMock.getUser().toString())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should get user detail")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldGetUserDetail() throws Exception {
        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, "detail")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should search users")
    @RunWithMockCustomUser(authorities = "ROLE_USER")
    void shouldSearchCharacters() throws Exception {
        String randomString = TestUtils.generateRandomString();
        UserNameGenderDTO dto = new UserNameGenderDTO();
        dto.setName(randomString);
        dto.setGender(GenderEnum.MALE);

        this.getMockMvc()
                .perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SerializationUtils.convertObjectToJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.status").value("user_success"));

        this.getMockMvc()
                .perform(get(buildUrl(BASE_URL, String.format("search?searchTerm=%s", randomString))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }
}
