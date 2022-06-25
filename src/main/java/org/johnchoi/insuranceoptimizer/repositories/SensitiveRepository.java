package org.johnchoi.insuranceoptimizer.repositories;


import org.johnchoi.insuranceoptimizer.entities.SensitiveEntity;
import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// A repository class: 1. opens a connection to database. 2. executes query. 3. maps the data to the entity object. 4. closes connection
@Repository
public interface SensitiveRepository extends JpaRepository<SensitiveEntity, Long> {

    List<SensitiveEntity> findAllByClient(UserEntity client);

}
