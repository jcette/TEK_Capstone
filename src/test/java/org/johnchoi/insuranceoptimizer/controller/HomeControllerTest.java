package org.johnchoi.insuranceoptimizer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testLogin() throws Exception {
        mockMvc.perform( get( "/login" ) ).andExpect( status( ).isOk( ) );
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform( get( "/home" ) ).andExpect( status( ).isOk( ) ).andExpect( view().name( "/index" ) );
    }
}