package org.johnchoi.insuranceoptimizer.models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/** This model class ferries data from the front-end supplied CSV file to the back end.
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HealthCSV {

    @CsvBindByName
    private String name;
    @CsvBindByName(column = "DOB")
    private String dateOfBirth;
    @CsvBindByName
    private String email;
    @CsvBindByName(column = "phone")
    private String phoneNumber;
    @CsvBindByName
    private String gender;
    @CsvBindByName
    private Integer age;
    @CsvBindByName
    private Double weight;
    @CsvBindByName
    private Double height;
    @CsvBindByName
    private String exercise;
    @CsvBindByName
    private String smoking;
    @CsvBindByName(column="drugs")
    private String substanceUse;
    @CsvBindByName
    private String diet;
    @CsvBindByName
    private Boolean priorCancer;
    @CsvBindByName(column="familyCancer")
    private Boolean familyCancerHistory;
    @CsvBindByName
    private Double bloodPressure;
    @CsvBindByName
    private Double cholesterol;
    @CsvBindByName(column="priorHeart")
    private Boolean priorHeartDisease;
    @CsvBindByName(column="familyHeart")
    private Boolean familyHeartDiseaseHistory;
    @CsvBindByName(column="insTier")
    private String tier;
    @CsvBindByName(column="insPremium")
    private String premium;
}
