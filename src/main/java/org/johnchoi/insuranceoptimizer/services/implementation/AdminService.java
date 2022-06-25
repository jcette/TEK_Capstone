package org.johnchoi.insuranceoptimizer.services.implementation;

import org.johnchoi.insuranceoptimizer.entities.*;
import org.johnchoi.insuranceoptimizer.exceptions.UserNotFoundException;
import org.johnchoi.insuranceoptimizer.models.*;

import org.johnchoi.insuranceoptimizer.models.constant.*;
import org.johnchoi.insuranceoptimizer.repositories.SensitiveRepository;
import org.johnchoi.insuranceoptimizer.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/** AdminService covers the business logic for both admins and clients because the client functions are simply a less powerful version of the admin functions.
 *
 */

@Service
public class AdminService {


    private SensitiveRepository sensitiveRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminService(SensitiveRepository sensitiveRepository, UserRepository userRepository) {
        this.sensitiveRepository = sensitiveRepository;
        this.userRepository = userRepository;
    }

    /** Transactional annotation makes this roll back changes if even one of the changes was incomplete. Data is either saved in all the tables or none.
     * This method takes our csv data (all the patient rows) and spits out a ClientData object which is later parsed into data objects of meaningful categories
     * @param csvData
     * @param clientEmail
     * @return
     * @throws UserNotFoundException
     */
    @Transactional
    public ClientData saveCsvData(List<HealthCSV> csvData, String clientEmail) throws UserNotFoundException {

        if(csvData==null ||csvData.isEmpty()){
            throw new RuntimeException("No Data");
        }

        //Retrieve the client entity using client email
        UserEntity userEntity = userRepository.findByEmail(clientEmail);
        List<SensitiveEntity> dataEntityList = new ArrayList<>();
        csvData.forEach(data->{
            //save personal data or sensitive data. Don't need to save other data objects because sensitiveEntity is linked one-to-one to the other tables
          SensitiveEntity sensitiveEntity = saveSensitiveData(data);
          sensitiveEntity.setClient(userEntity);
            dataEntityList.add( sensitiveEntity );
        });
        //save all
        this.sensitiveRepository.saveAllAndFlush(dataEntityList);

      ClientData clientData =   populateClientData( userEntity, dataEntityList );
        return clientData;
    }

    /** Though this method is named "saveSensitiveData" it really is "saveAllData(except users)" because the sensitiveData is linked one-to-one with the other tables.
     * This enables us to simultaneously create: healthEntity object, financeEntity object, and predictionEntity object in one go.
     *
     * @param data
     * @return
     */
    private SensitiveEntity saveSensitiveData(HealthCSV data) {
        SensitiveEntity sensitiveEntity =  new SensitiveEntity();
        sensitiveEntity.setName(data.getName());
        sensitiveEntity.setEmail(data.getEmail());
        sensitiveEntity.setGender(data.getGender());
        sensitiveEntity.setPhoneNumber(data.getPhoneNumber());
        sensitiveEntity.setDateOfBirth(convertStringToDate(data.getDateOfBirth()));
       // sensitiveEntity.setClient();

        HealthEntity healthEntity = saveHealthData(data);
        FinanceEntity financeEntity = saveFinancialData(data);
        sensitiveEntity.setHealth(healthEntity);
        sensitiveEntity.setFinance(financeEntity);

        // generate predictions
        PredictionEntity predictionEntity = generatePrediction(data);
        sensitiveEntity.setPrediction(predictionEntity);

        return sensitiveEntity;
    }

    private PredictionEntity generatePrediction(HealthCSV data) {
        PredictionEntity predictionEntity = new PredictionEntity();
        predictionEntity.setCancer(getPrediction());
        predictionEntity.setHeart(getPrediction());
        predictionEntity.setCancerRecommendation(Recommendation.randomRecommendation());
        predictionEntity.setHeartRecommendation(Recommendation.randomRecommendation());
        predictionEntity.setRecommendation(Recommendation.randomRecommendation());
        return predictionEntity;
    }

    // For proof of concept, generating random float between 0.0 and 100.0; in future, value should be provided by regression algorithm or machine-learning algo
    private Float getPrediction() {
        Float prediction = Float.valueOf(new Random().nextInt(1000,10000) / 100);
        return prediction;
    }

    private Date convertStringToDate(String date){
        return new Date();
    }

    private HealthEntity saveHealthData(HealthCSV data) {
        HealthEntity healthEntity = new HealthEntity();
        healthEntity.setAge(data.getAge());
        healthEntity.setCholesterol(data.getCholesterol());
        healthEntity.setBloodPressure(data.getBloodPressure());
        healthEntity.setDiet(Diet.getDiet(data.getDiet()));
        healthEntity.setExercise(Exercise.getExercise(data.getExercise()));
        healthEntity.setSmoking(Smoking.getSmoking((data.getSmoking())));
        healthEntity.setSubstanceUse(SubstanceUse.getSunStanceUse(data.getSubstanceUse()));
        healthEntity.setFamilyCancerHistory(data.getFamilyCancerHistory());
        healthEntity.setFamilyHeartDiseaseHistory(data.getFamilyHeartDiseaseHistory());
        healthEntity.setPriorCancer(data.getPriorCancer());
        healthEntity.setPriorHeartDisease(data.getPriorHeartDisease());
        healthEntity.setWeight(data.getWeight());
        healthEntity.setHeight(data.getHeight());
      //  this.healthRepository.save(healthEntity);
        return healthEntity;
    }

    private FinanceEntity saveFinancialData(HealthCSV data) {
        FinanceEntity financeEntity = new FinanceEntity();
        Premium premium = Arrays.stream(Premium.values()).filter(
                (t) -> t.name().equalsIgnoreCase(data.getPremium())).findFirst().orElse(Premium.NO_DATA);
        Tier tier = Arrays.stream(Tier.values()).filter(
                (t) -> t.name().equalsIgnoreCase(data.getTier())).findFirst().orElse(Tier.NO_DATA);
        financeEntity.setPremium(premium);
        financeEntity.setTier(tier);

        return financeEntity;
    }


    @Transactional
    public ClientData getClientData(String clientEmail) {
        UserEntity clientEntity  = this.userRepository.findByEmail( clientEmail );
        List<SensitiveEntity> clientDataList = this.sensitiveRepository.findAllByClient(clientEntity);
        ClientData clientData = populateClientData( clientEntity, clientDataList );
        return clientData;
    }

    private ClientData populateClientData(UserEntity clientEntity, List<SensitiveEntity> clientDataList) {
        List<SensitiveData> sensitiveDataList = new ArrayList<>();
        List<CancerData> cancerDataList = new ArrayList<>();
        List<HeartData> heartDataList =  new ArrayList<>();
        List<FinanceData> financeDataList = new ArrayList<>();
        List<PredictionData> predictionDataList = new ArrayList<>();

        populateAllListData( clientDataList, sensitiveDataList, cancerDataList, heartDataList, financeDataList, predictionDataList);

        User client = new User();
        BeanUtils.copyProperties( clientEntity, client );
        ClientData clientData =  ClientData.builder( )
                .Client( client )
                .cancerData( cancerDataList )
                .heartData( heartDataList )
                .sensitiveData( sensitiveDataList )
                .financeData(financeDataList)
                .predictionData(predictionDataList)
                .build( );

        //generate the group prediction
        generateGroupPrediction(clientData);
        return clientData;
    }

    private void generateGroupPrediction(ClientData clientData) {
        List<GroupPrediction> groupPredictions = new ArrayList<>( );

        if(clientData.getCancerData()!=null && !clientData.getCancerData().isEmpty() ){
            //create Group Prediction for Cancer
            GroupPrediction  prediction = new GroupPrediction( );
            prediction.setDecrease((double) (new Random().nextInt(1000,4000)/100));
            prediction.setAverageRisk((double) (new Random().nextInt(2000,7000)/100));
            prediction.setMaintain((double) (new Random().nextInt(3000,6000)/100));
            prediction.setIncrease((double) (new Random().nextInt(3000,5000)/100));
            prediction.setType( "Cancer" );
            groupPredictions.add( prediction );
        }

        if(clientData.getHeartData()!=null && !clientData.getHeartData().isEmpty()){
            //create Group Prediction for Heart
            GroupPrediction  prediction = new GroupPrediction( );
            prediction.setDecrease((double) (new Random().nextInt(1000,4000)/100));
            prediction.setAverageRisk((double) (new Random().nextInt(2000,7000)/100));
            prediction.setMaintain((double) (new Random().nextInt(3000,6000)/100));
            prediction.setIncrease((double) (new Random().nextInt(3000,5000)/100));
            prediction.setType( "Heart" );
            groupPredictions.add( prediction );
        }
        clientData.setGroupPredictions(groupPredictions);
    }

    private void populateAllListData(List<SensitiveEntity> clientDataList, List<SensitiveData> sensitiveDataList, List<CancerData> cancerDataList, List<HeartData> heartDataList, List<FinanceData> financeDataList, List<PredictionData> predictionDataList) {
        clientDataList.forEach(sensitiveEntity -> {
            SensitiveData sensitiveData = populateSensitiveData(sensitiveEntity);
            sensitiveDataList.add( sensitiveData );

            FinanceData financeData = populateFinanceData(sensitiveEntity.getFinance());
            financeDataList.add(financeData);

            PredictionData predictionData = populatePredictionData(sensitiveEntity.getPrediction());
            predictionDataList.add(predictionData);

           // check for cancer data conditions
            HealthEntity healthEntity = sensitiveEntity.getHealth();
            PredictionEntity predictionEntity = sensitiveEntity.getPrediction();

           if(healthEntity.getFamilyCancerHistory() != null || healthEntity.getPriorCancer() != null){
               CancerData cancerData =   populateCancerData(healthEntity, predictionEntity);
               cancerDataList.add( cancerData );
           }

           if (healthEntity.getFamilyHeartDiseaseHistory() != null || healthEntity.getPriorHeartDisease() != null || healthEntity.getBloodPressure() != null || healthEntity.getCholesterol() != null) {

               HeartData heartData = populateHeartData(healthEntity, predictionEntity);
               heartDataList.add(heartData);
           }

        } );

    }

    private void populateCommonHealthData(HealthData healthData, HealthEntity healthEntity, PredictionEntity prediction) {
        healthData.setAge(healthEntity.getAge());
        healthData.setDiet(healthEntity.getDiet());
        healthData.setSmoking(healthEntity.getSmoking());
        healthData.setSubstanceUse(healthEntity.getSubstanceUse());
        healthData.setHeight(healthEntity.getHeight());
        healthData.setWeight(healthEntity.getWeight());
        healthData.setExercise(healthEntity.getExercise());
        healthData.setId(healthEntity.getId());
        healthData.setOverallRecommendation(prediction.getRecommendation());
    }

    private HeartData populateHeartData(HealthEntity health, PredictionEntity prediction) {
        HeartData heartData = new HeartData();
        populateCommonHealthData(heartData, health, prediction);
        heartData.setPriorHeartDisease(health.getPriorHeartDisease());
        heartData.setFamilyHeartDiseaseHistory(health.getFamilyHeartDiseaseHistory());
        heartData.setCholesterol(health.getCholesterol());
        heartData.setBloodPressure(health.getBloodPressure());
        heartData.setHeartPrediction(prediction.getHeart());
        heartData.setHeartRecommendation(prediction.getHeartRecommendation());

        return heartData;
    }

    private CancerData populateCancerData(HealthEntity health, PredictionEntity prediction) {
        CancerData cancerData = new CancerData();
        populateCommonHealthData(cancerData, health, prediction);
        cancerData.setPriorCancer(health.getPriorCancer());
        cancerData.setFamilyCancerHistory(health.getFamilyCancerHistory());
        cancerData.setCancerPrediction(prediction.getCancer());
        cancerData.setCancerRecommendation(prediction.getCancerRecommendation());

        return cancerData;
    }

    private SensitiveData populateSensitiveData(SensitiveEntity sensitiveEntity) {
        SensitiveData sensitiveData = new SensitiveData();
        sensitiveData.setName(sensitiveEntity.getName());
        sensitiveData.setGender(sensitiveEntity.getGender());
        sensitiveData.setDateOfBirth(sensitiveEntity.getDateOfBirth());
        sensitiveData.setPhoneNumber(sensitiveEntity.getPhoneNumber());
        sensitiveData.setEmail(sensitiveEntity.getEmail());
        return sensitiveData;
    }

    private FinanceData populateFinanceData(FinanceEntity financeEntity) {
        FinanceData financeData = new FinanceData();
        financeData.setPremium(financeEntity.getPremium());
        financeData.setTier(financeEntity.getTier());
        return  financeData;
    }

    private PredictionData populatePredictionData(PredictionEntity predictionEntity) {
        PredictionData predictionData = new PredictionData();
        predictionData.setCancerPrediction(predictionEntity.getCancer());
        predictionData.setHeartPrediction(predictionEntity.getHeart());
        predictionData.setCancerRecommendation(predictionEntity.getCancerRecommendation());
        predictionData.setHeartRecommendation(predictionEntity.getHeartRecommendation());
        predictionData.setOverallRecommendation(predictionEntity.getRecommendation());
        return predictionData;
    }

    public ClientDataForm getClientDataById(Long dataId) {
        SensitiveEntity sensitiveEntity = this.sensitiveRepository.findById(dataId).get();
        SensitiveData sensitiveData = populateSensitiveData(sensitiveEntity);
        // check for cancer data conditions
        HealthEntity healthEntity = sensitiveEntity.getHealth();
        PredictionEntity predictionEntity = sensitiveEntity.getPrediction();
        HealthData healthData=null;
        if(healthEntity.getFamilyCancerHistory() != null || healthEntity.getPriorCancer() != null){
            healthData =   populateCancerData(healthEntity, predictionEntity);
        }

        if (healthEntity.getFamilyHeartDiseaseHistory() != null || healthEntity.getPriorHeartDisease() != null || healthEntity.getBloodPressure() != null || healthEntity.getCholesterol() != null) {

            healthData = populateHeartData(healthEntity, predictionEntity);
        }
        ClientDataForm clientDataForm = ClientDataForm.builder( ).dataId( dataId ).healthData(healthData).sensitiveData(sensitiveData).build( );
        return clientDataForm;
    }

    @Transactional
    public void deleteDataById(Long dataId) {
        this.sensitiveRepository.deleteById( dataId );
    }

    @Transactional
    public void updateRecord(ClientDataForm form) throws Exception {
        SensitiveEntity sensitiveEntity = this.sensitiveRepository.findById(form.getDataId()).get();

        if(sensitiveEntity==null){
            throw  new RuntimeException( "Invalid data Id" );
        }

       HealthData healthData=  form.getHealthData();
            HealthEntity healthEntity = sensitiveEntity.getHealth();
        BeanUtils.copyProperties( healthData,  healthEntity);
        healthEntity.setId( form.getDataId( ) );
       SensitiveData sensitiveData = form.getSensitiveData();
        BeanUtils.copyProperties( sensitiveData,  sensitiveEntity);
        sensitiveEntity.setId( form.getDataId( ) );
        sensitiveEntity.setHealth( healthEntity );
        this.sensitiveRepository.save(sensitiveEntity );

    }
}
