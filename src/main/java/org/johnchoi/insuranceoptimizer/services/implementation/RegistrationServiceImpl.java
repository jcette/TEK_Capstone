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

    /**
     * Allows us to perform database operations
     */
    private UserRepository userRepository;

    /**
     * Used to encode/scramble user password
     */
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository,  PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *  Takes input from registration page and saves into backend using userEntity object
     * @param userRequest : input from front end
     * @return : gives error if failed to register; otherwise gives success message
     */
    @Override
    public String registerUser(User userRequest) {
        try {


            UserEntity userEntity = new UserEntity();

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
