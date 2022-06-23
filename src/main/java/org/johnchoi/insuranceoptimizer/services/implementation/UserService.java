package org.johnchoi.insuranceoptimizer.services.implementation;

import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.exceptions.UserNotFoundException;
import org.johnchoi.insuranceoptimizer.models.User;
import org.johnchoi.insuranceoptimizer.models.constant.UserRoles;
import org.johnchoi.insuranceoptimizer.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        UserEntity userEntity = this.userRepository.findByEmail(email);
        if(userEntity==null){
            throw new UserNotFoundException("User Not Found");
        }
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        return user;
    }

    public List<User> getClients() {
        List<User> clientList = new ArrayList<>();
       List<UserEntity> userEntityList = userRepository.findAllByUserRole(UserRoles.CLIENT);
       if(userEntityList!=null && !userEntityList.isEmpty())
       {
           userEntityList.forEach(user->{
               User client = new User();
               BeanUtils.copyProperties(user, client);
               clientList.add(client);

           });
       }

       return clientList;
    }
}
