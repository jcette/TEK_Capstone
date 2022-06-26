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
          SensitiveEntity sensitiveEntity = createSensitiveEntityToBeSaved(data);
          sensitiveEntity.setClient(userEntity);
            dataEntityList.add( sensitiveEntity );
        });
        //save all
        this.sensitiveRepository.saveAllAndFlush(dataEntityList);

      ClientData clientData =   populateClientData( userEntity, dataEntityList );
        return clientData;
    }

    /** Though this method is named "createSensitiveEntityToBeSaved" it really creates all entities to be saved, because the sensitive entity is linked one-to-one with the other tables.
     * This enables us to simultaneously create: healthEntity object, financeEntity object, and predictionEntity object in one go.
     * After this creates the entities, the saveCSVData method will use the entities to store into the actual database (in maria DB)
     *
     * @param data
     * @return
     */
    private SensitiveEntity createSensitiveEntityToBeSaved(HealthCSV data) {
        SensitiveEntity sensitiveEntity =  new SensitiveEntity();
        sensitiveEntity.setName(data.getName());
        sensitiveEntity.setEmail(data.getEmail());
        sensitiveEntity.setGender(data.getGender());
        sensitiveEntity.setPhoneNumber(data.getPhoneNumber());
        sensitiveEntity.setDateOfBirth(convertStringToDate(data.getDateOfBirth()));
       // sensitiveEntity.setClient();

        HealthEntity healthEntity = createHealthDataToBeSaved(data);
        FinanceEntity financeEntity = createFinanceDataToBeSaved(data);
        sensitiveEntity.setHealth(healthEntity);
        sensitiveEntity.setFinance(financeEntity);

        // generate predictions
        PredictionEntity predictionEntity = generatePrediction(data);
        sensitiveEntity.setPrediction(predictionEntity);

        return sensitiveEntity;
    }

    /** Currently this method generates predictions based off of the getPrediction method, which generates random predictions bound between certain %s between 0 and 100
     * To-do: This method should eventually generate predictions off the data stored in mariadb, from the health-related parameters
     * It should also generate recommendations by some algorithm that evaluates the predictions and the current level of insurance coverage
     *
     * @param data
     * @return
     */
    private PredictionEntity generatePrediction(HealthCSV data) {
        PredictionEntity predictionEntity = new PredictionEntity();
        predictionEntity.setCancer(getPrediction());
        predictionEntity.setHeart(getPrediction());
        predictionEntity.setCancerRecommendation(Recommendation.randomRecommendation());
        predictionEntity.setHeartRecommendation(Recommendation.randomRecommendation());
        predictionEntity.setRecommendation(Recommendation.randomRecommendation());
        return predictionEntity;
    }

    /** For proof of concept, generating random float between 0.0 and 100.0; in future, value should be provided by regression algorithm or machine-learning algo
     * Eventually should be replaced by a regression that better approximates one you would see in reality, or by an algorithm provided by a machine learning kit
     * @return
     */
    private Float getPrediction() {
        Float prediction = Float.valueOf(new Random().nextInt(1000,10000) / 100);
        return prediction;
    }

    /**
     * Simple method that helps me work with dates
     * @param date
     * @return
     */
    private Date convertStringToDate(String date){
        return new Date();
    }

    /**
     * method that parses the csv data (stored in a HealthCSV object) and generates a health entity
     * @param data
     * @return
     */
    private HealthEntity createHealthDataToBeSaved(HealthCSV data) {
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

    /**
     * Method that parses the HealthCSV object, takes the finance parameters and creates a finance entity object. Supplants any misfit inputs to "NO_DATA", which must be edited directly in the csv or later in the admin edit page
     * @param data
     * @return
     */
    private FinanceEntity createFinanceDataToBeSaved(HealthCSV data) {
        FinanceEntity financeEntity = new FinanceEntity();
        Premium premium = Arrays.stream(Premium.values()).filter(
                (t) -> t.name().equalsIgnoreCase(data.getPremium())).findFirst().orElse(Premium.NO_DATA);
        Tier tier = Arrays.stream(Tier.values()).filter(
                (t) -> t.name().equalsIgnoreCase(data.getTier())).findFirst().orElse(Tier.NO_DATA);
        financeEntity.setPremium(premium);
        financeEntity.setTier(tier);

        return financeEntity;
    }

    /**
     * Method to retrieve all entities tied to a particular client, from the backend. First finds the particular user by the client's email, then uses all the data entities tied to that client, transfers the data from the entities to data objects,
     * @param clientEmail
     * @return
     */
    @Transactional
    public ClientData getClientData(String clientEmail) {
        UserEntity clientEntity  = this.userRepository.findByEmail( clientEmail );
        List<SensitiveEntity> clientDataList = this.sensitiveRepository.findAllByClient(clientEntity);
        ClientData clientData = populateClientData( clientEntity, clientDataList );
        return clientData;
    }

    /**
     * Copies data from database entities to data model objects to eventually display on front end
     * @param clientEntity
     * @param clientDataList
     * @return
     */
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

    /**
     * Currently creates group predictions based on random numbers
     * To-do: create predictions based on the average of individual predictions
     * To-do: create recommendations based on the average of individual recommendations (what % of individuals should decrease/maintain/increase coverage?)
     * @param clientData
     */
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

    /**
     * Copies data from database entities to data model objects to eventually display on front end
     * @param clientDataList
     * @param sensitiveDataList
     * @param cancerDataList
     * @param heartDataList
     * @param financeDataList
     * @param predictionDataList
     */
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

    /**
     * Method encompasses the creation of common health data, from the respective entity, that will be used in both heart and cancer predictions
     * @param healthData
     * @param healthEntity
     * @param prediction
     */
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

    /**
     * Method takes common health data and adds relevant heart data the heart data model object
     * @param health
     * @param prediction
     * @return
     */
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

    /**
     * Takes common health data and adds relevant cancer data to cancer data model object
     * @param health
     * @param prediction
     * @return
     */
    private CancerData populateCancerData(HealthEntity health, PredictionEntity prediction) {
        CancerData cancerData = new CancerData();
        populateCommonHealthData(cancerData, health, prediction);
        cancerData.setPriorCancer(health.getPriorCancer());
        cancerData.setFamilyCancerHistory(health.getFamilyCancerHistory());
        cancerData.setCancerPrediction(prediction.getCancer());
        cancerData.setCancerRecommendation(prediction.getCancerRecommendation());

        return cancerData;
    }

    /**
     * Takes sensitive data from entity and adds to sensitive data object
     * @param sensitiveEntity
     * @return
     */
    private SensitiveData populateSensitiveData(SensitiveEntity sensitiveEntity) {
        SensitiveData sensitiveData = new SensitiveData();
        sensitiveData.setName(sensitiveEntity.getName());
        sensitiveData.setGender(sensitiveEntity.getGender());
        sensitiveData.setDateOfBirth(sensitiveEntity.getDateOfBirth());
        sensitiveData.setPhoneNumber(sensitiveEntity.getPhoneNumber());
        sensitiveData.setEmail(sensitiveEntity.getEmail());
        return sensitiveData;
    }

    /**
     * Takes financial data from entity and creates finance data object
     * @param financeEntity
     * @return
     */

    private FinanceData populateFinanceData(FinanceEntity financeEntity) {
        FinanceData financeData = new FinanceData();
        financeData.setPremium(financeEntity.getPremium());
        financeData.setTier(financeEntity.getTier());
        return  financeData;
    }

    /**
     * Currently creates predictions based on bound random numbers but eventually should generate predictions based on the health data
     * @param predictionEntity
     * @return
     */
    private PredictionData populatePredictionData(PredictionEntity predictionEntity) {
        PredictionData predictionData = new PredictionData();
        predictionData.setCancerPrediction(predictionEntity.getCancer());
        predictionData.setHeartPrediction(predictionEntity.getHeart());
        predictionData.setCancerRecommendation(predictionEntity.getCancerRecommendation());
        predictionData.setHeartRecommendation(predictionEntity.getHeartRecommendation());
        predictionData.setOverallRecommendation(predictionEntity.getRecommendation());
        return predictionData;
    }

    /** Finds all client data from backend and uses it to populate edit table on front end (for one row, identified by id)
     *
     * @param dataId
     * @return
     */
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

    /**
     * Finds a particular row in the database and deletes it from database
     * @param dataId
     */
    @Transactional
    public void deleteDataById(Long dataId) {
        this.sensitiveRepository.deleteById( dataId );
    }

    /**
     * The row that you edit on the edit-page, gets saved into the database here
     * @param form
     * @throws Exception
     */
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
