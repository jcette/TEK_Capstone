package org.johnchoi.insuranceoptimizer.repositories;

import org.johnchoi.insuranceoptimizer.entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class SensitiveRepositoryTest {

    @Autowired
    private SensitiveRepository sensitiveRepository;
    private SensitiveEntity sensitiveEntity;

    @BeforeEach
    public void setup(){
       sensitiveEntity = new SensitiveEntity();
        HealthEntity healthEntity = new HealthEntity();
        PredictionEntity prediction = new PredictionEntity();

        UserEntity client = new UserEntity();
        FinanceEntity finance = new FinanceEntity();
        sensitiveEntity.setHealth( healthEntity );
        sensitiveEntity.setPrediction(prediction);
        sensitiveEntity.setClient( client );
        sensitiveEntity.setFinance( finance );
    }
    @Test
    void should_find_no_data_if_repository_empty() {
        List<SensitiveEntity> sensitiveEntityList = sensitiveRepository.findAll();
        assertThat(sensitiveEntityList);
    }

    @Test
    void should_save_sensitive_data(){
        sensitiveEntity =  sensitiveRepository.save( sensitiveEntity );

        assertThat( sensitiveEntity.getId() ).isNotNull();
        assertThat(sensitiveEntity.getHealth().getId()).isNotNull();
        assertThat( sensitiveEntity.getPrediction().getId() ).isNotNull();
        assertThat( sensitiveEntity.getFinance().getId() ).isNotNull();
    }

    @Test
    void should_find_sensitive_data_by_id(){
        sensitiveEntity =  sensitiveRepository.save( sensitiveEntity );

        SensitiveEntity findEntity = sensitiveRepository.findById( sensitiveEntity.getId() ).get();
        assertThat( sensitiveEntity.getId() ).isNotNull();
        assertThat(sensitiveEntity.getHealth().getId()).isNotNull();
        assertThat( sensitiveEntity.getPrediction().getId() ).isNotNull();
        assertThat( sensitiveEntity.getFinance().getId() ).isNotNull();
    }

    @Test
    void should_delete_data_by_id(){
        //save data
        sensitiveEntity =  sensitiveRepository.save( sensitiveEntity );
        //delete data
        long dataId = sensitiveEntity.getId();
        sensitiveRepository.deleteById(dataId);
        //get again
        assertTrue( sensitiveRepository.findById( dataId ).isEmpty() );
    }


}