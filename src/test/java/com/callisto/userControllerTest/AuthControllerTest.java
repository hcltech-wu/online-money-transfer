package com.callisto.userControllerTest;

import com.callisto.controller.AuthController;
import com.callisto.dto.LoginRequest;
import com.callisto.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLoginSuccess() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("password123");

        given(userService.isUserValid("user@example.com", "password123")).willReturn("User login successfully");

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
                .andExpect(content().string("User login successfully"));
    }

    @Test
    void testLoginFailureUserNotFound() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("password123");

        given(userService.isUserValid("nonexistent@example.com", "password123")).willReturn("User not found");

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
                .andExpect(content().string("User not found"));
    }

    @Test
    void testLoginFailureIncorrectPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("wrongpassword");

        given(userService.isUserValid("user@example.com", "wrongpassword")).willReturn("Incorrect password");

        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
                .andExpect(content().string("Incorrect password"));
    }
}
