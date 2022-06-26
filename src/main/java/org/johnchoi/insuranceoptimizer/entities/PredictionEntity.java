package org.johnchoi.insuranceoptimizer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.constant.Recommendation;

import javax.persistence.*;

/**
 * Represents prediction table in database. Is not populated by the csv but is populated by an internal algo (currently using random number generators but eventually to use the health data and machine learning kit)
 */
@Data
@NoArgsConstructor
@Table(name="Health_Predictions_And_Recommendations")
@Entity
public class PredictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Cancer_Prediction")
    private Float cancer;
    @Column(name="Heart_Prediction")
    private Float heart;
    @Column(name="Cancer_Insurance_Recommendation")
    private Recommendation cancerRecommendation;
    @Column(name="Heart_Insurance_Recommendation")
    private Recommendation heartRecommendation;
    @Column(name="Overall_Recommendation")
    private Recommendation recommendation;

}
