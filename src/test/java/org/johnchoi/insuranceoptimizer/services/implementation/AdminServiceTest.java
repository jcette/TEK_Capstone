package org.johnchoi.insuranceoptimizer.services.implementation;

import org.johnchoi.insuranceoptimizer.entities.HealthEntity;
import org.johnchoi.insuranceoptimizer.entities.PredictionEntity;
import org.johnchoi.insuranceoptimizer.entities.SensitiveEntity;
import org.johnchoi.insuranceoptimizer.entities.UserEntity;
import org.johnchoi.insuranceoptimizer.exceptions.UserNotFoundException;
import org.johnchoi.insuranceoptimizer.models.*;
import org.johnchoi.insuranceoptimizer.models.constant.Recommendation;
import org.johnchoi.insuranceoptimizer.models.constant.UserRoles;
import org.johnchoi.insuranceoptimizer.repositories.SensitiveRepository;
import org.johnchoi.insuranceoptimizer.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private SensitiveRepository sensitiveRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    List<HealthCSV> healthCSVList=new ArrayList<>(  );

    @BeforeEach
    public void setup() throws IOException {

        healthCSVList = new ArrayList<>(  );
        HealthCSV csv = new HealthCSV();
        csv.setAge( 10 );
        csv.setName( "TEST" );
        csv.setExercise( "REGULAR" );
        csv.setDiet( "HEALTHY" );
        healthCSVList.add( csv );

    }

    @DisplayName(value = "JUnit test for saving csv data")
    @Test
    void testSaveCsvData() throws UserNotFoundException {

        UserEntity entity = new UserEntity();
        entity.setName( "TEST" );
        entity.setEmail( "a@a.com" );
        entity.setUserRole( UserRoles.ADMIN );
        //given
        given(userRepository.findByEmail( anyString() )).willReturn( entity );

        // save
        ClientData clientData =    adminService.saveCsvData( healthCSVList, "a@a.com"  );
        assertThat(clientData).isNotNull();
        assertThat(clientData.getHeartData()).isNotNull();

    }

    @DisplayName(value = "JUnit test for Get Client Data")
    @Test
    void getClientData() {
        UserEntity entity = new UserEntity();
        entity.setName( "TEST" );
        entity.setEmail( "a@a.com" );
        entity.setUserRole( UserRoles.CLIENT );
        //given
        given(userRepository.findByEmail( anyString() )).willReturn( entity );

        ClientData clientData =     adminService.getClientData( "a@a.com" );
        assertThat(clientData).isNotNull();
        assertThat(clientData.getHeartData()).isNotNull();


    }

    @DisplayName(value = "JUnit test for get client data by dataId")
    @Test
    void getClientDataById() {
        SensitiveEntity sensitiveEntity = new SensitiveEntity();
        HealthEntity healthEntity = new HealthEntity();
        healthEntity.setFamilyCancerHistory( true);

        PredictionEntity prediction = new PredictionEntity();
        prediction.setRecommendation( Recommendation.INCREASE );
        sensitiveEntity.setPrediction( prediction );
        sensitiveEntity.setHealth( healthEntity );
        given( sensitiveRepository.findById( anyLong() ) ).willReturn( Optional.of(sensitiveEntity) );
        ClientDataForm clientDataForm = adminService.getClientDataById( 1L );
        assertThat(clientDataForm).isNotNull();
        assertThat( clientDataForm.getHealthData() ).isNotNull();
    }

    @DisplayName("JUnit test for delete Data from Sensitive Table")
    @Test
    void deleteDataById() {
        // given - precondition or setup
        long dataId =1 ;
        doNothing().when( sensitiveRepository ).deleteById( anyLong() );
        // when -  action or the behaviour that we are going test
        adminService.deleteDataById( dataId );
        // then - verify the output
        verify(sensitiveRepository, times(1)).deleteById(dataId);

    }

}