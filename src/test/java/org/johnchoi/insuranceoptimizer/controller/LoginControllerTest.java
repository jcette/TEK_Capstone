package org.johnchoi.insuranceoptimizer.controller;

import org.johnchoi.insuranceoptimizer.models.LoginRequest;
import org.johnchoi.insuranceoptimizer.services.implementation.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void showLoginPage() throws Exception {
        LoginRequest user = new LoginRequest();
        ModelAndView modelAndView = new ModelAndView(  );
        mockMvc.perform( get( "/login", modelAndView, user ))
                .andExpect(status().isOk() )
                .andExpect(view().name( "/login" ));
    }

    @Test
    void processLogin() throws Exception  {
        LoginRequest user = new LoginRequest();
        user.setEmail( "a@a.com" );
        user.setPassword( "TEST" );
        ModelAndView modelAndView = new ModelAndView(  );

        mockMvc.perform( post( "/login", modelAndView, user ))
                .andExpect(status().is3xxRedirection() );
    }
}