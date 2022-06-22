package org.johnchoi.insuranceoptimizer.services.implementation;

import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.models.User;
import org.johnchoi.insuranceoptimizer.repositories.UserRepository;
import org.johnchoi.insuranceoptimizer.services.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User userRequest;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void should_register_user_successfully() {

        userRequest = new User();
        userRequest.setPassword("ABCDEF");
        userRequest.setEmail("a@a.com");

      String output =   registrationService.registerUser(userRequest);
      assertEquals("User registered successfully", output);


    }

//    @Test void should_throw_error_if_user_already_registered() {
//
//
//    }
}