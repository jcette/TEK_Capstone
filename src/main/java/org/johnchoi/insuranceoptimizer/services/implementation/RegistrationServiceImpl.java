package org.johnchoi.insuranceoptimizer.services.implementation;

import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.models.User;
import org.johnchoi.insuranceoptimizer.repositories.UserRepository;
import org.johnchoi.insuranceoptimizer.services.RegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository,  PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(User userRequest) {
        try {

// check if user exist
         /* UserEntity userExist =    this.userRepository.findByEmail(userRequest.getEmail());

         if(userExist!=null){
             //throw error, user already exists; do not need this because of unique constraint
         }
         */

            UserEntity userEntity = new UserEntity();
            //To-do: check whether userRequest already exists in database

            BeanUtils.copyProperties(userRequest, userEntity); // instead of using many getters and setters; only works if userRequest and userEntity has exact same variable names
            userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            this.userRepository.save(userEntity);
            return "User registered successfully";
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // throw .//To-do: write custom exception here
        }
    }
}
