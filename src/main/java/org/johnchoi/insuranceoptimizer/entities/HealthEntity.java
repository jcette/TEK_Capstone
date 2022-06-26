package org.johnchoi.insuranceoptimizer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.constant.Diet;
import org.johnchoi.insuranceoptimizer.models.constant.Exercise;
import org.johnchoi.insuranceoptimizer.models.constant.Smoking;
import org.johnchoi.insuranceoptimizer.models.constant.SubstanceUse;

import javax.persistence.*;

/** Represents all health information in database. Includes cancer, heart, and shared health parameters
 *
 */
@Data
@NoArgsConstructor
@Table(name="Health_Information")
@Entity
public class HealthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Age")
    private Integer age;
    @Column(name="Height")
    private Double height;
    @Column(name="Weight")
    private Double weight;
    @Column(name="Exercise")
    private Exercise exercise;
    @Column(name="Diet")
    private Diet diet;
    @Column(name="Smoking")
    private Smoking smoking;
    @Column(name="Substance_Use")
    private SubstanceUse substanceUse;
    @Column(name="Cancer_History")
    private Boolean priorCancer;
    @Column(name="Family_Cancer_History")
    private Boolean familyCancerHistory;
    @Column(name="Blood_Pressure")
    private Double bloodPressure;
    @Column(name="Cholesterol")
    private Double cholesterol;
    @Column(name="Heart_History")
    private Boolean priorHeartDisease;
    @Column(name="Family_Heart_History")
    private Boolean familyHeartDiseaseHistory;

}
