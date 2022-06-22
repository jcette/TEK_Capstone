package org.johnchoi.insuranceoptimizer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.Recommendation;

import javax.persistence.*;

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
