package org.johnchoi.insuranceoptimizer.repositories;

import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.models.constant.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, Long>{

    /**
     * retrieves particular user data using email
     * @param email
     * @return
     */
    public UserEntity findByEmail(String email);

    /**
     * retrieves particular user information using both email and password
     * @param email
     * @param password
     * @return
     */
    public UserEntity findByEmailAndPassword(String email, String password);

    /**
     * retrieves a list of users that have a particular user role
     * @param userRole
     * @return
     */
    public List<UserEntity> findAllByUserRole(UserRoles userRole);

}
