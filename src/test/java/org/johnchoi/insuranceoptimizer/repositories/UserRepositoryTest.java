package org.johnchoi.insuranceoptimizer.repositories;

import org.aspectj.lang.annotation.Before;
import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.models.UserRoles;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity adminUser;

    private UserEntity clientUser;

    @BeforeEach
     void setup(){
        adminUser = new UserEntity();
        adminUser.setUserRole( UserRoles.ADMIN );
        adminUser.setEmail( "admin@test.com" );


        clientUser = new UserEntity();
        clientUser.setUserRole( UserRoles.CLIENT );
        clientUser.setName( "Client" );
        clientUser.setEmail( "client@test.com" );
    }
    @Test
    public void should_not_return_user_if_not_exist(){
        UserEntity user = userRepository.findByEmail( "test@test.com" );
        assertThat(user).isNull();
    }

    @Test
    public void should_save_admin_user(){
        userRepository.save( adminUser );
        assertThat( adminUser.getId() ).isNotNull();
    }

    @Test
    public void should_save_client_user(){
        userRepository.save( clientUser );
        assertThat( clientUser.getId() ).isNotNull();
    }

    @Test
    public void should_find_user_by_email_id(){
        userRepository.save( adminUser );
      UserEntity user =   userRepository.findByEmail( "admin@test.com" );
      assertEquals( "admin@test.com", user.getEmail() );
      assertEquals( UserRoles.ADMIN, user.getUserRole() );
    }

}