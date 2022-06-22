package org.johnchoi.insuranceoptimizer.repositories;

import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, Long>{

    public UserEntity findByEmail(String email);

    public UserEntity findByEmailAndPassword(String email, String password);

    public List<UserEntity> findAllByUserRole(UserRoles userRole);

}
