package org.johnchoi.insuranceoptimizer.services.implementation;

import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.exceptions.UserNotFoundException;
import org.johnchoi.insuranceoptimizer.models.User;
import org.johnchoi.insuranceoptimizer.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void should_find_user_by_email() throws UserNotFoundException {
        String email="a@a.com";
        //setup
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        given(userRepository.findByEmail(anyString())).willReturn(userEntity);
        //act or perform the action
      User user = userService.findUserByEmail(email);
      //verify
      assertThat(user).isNotNull();
      assertEquals(email, user.getEmail());


    }

    @Test
    void should_throw_user_not_found_exception() throws UserNotFoundException {
        String email="a@a.com";
        //setup
        given(userRepository.findByEmail(anyString())).willReturn(null);
        //act or perform the action

       UserNotFoundException exception= assertThrows(UserNotFoundException.class, ()->userService.findUserByEmail(email));

       assertEquals("User Not Found", exception.getMessage());
    }

}