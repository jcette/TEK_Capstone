package org.johnchoi.insuranceoptimizer.repositories;


import org.johnchoi.insuranceoptimizer.entities.SensitiveEntity;
import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensitiveRepository extends JpaRepository<SensitiveEntity, Long> {

    List<SensitiveEntity> findAllByClient(UserEntity client);

}
