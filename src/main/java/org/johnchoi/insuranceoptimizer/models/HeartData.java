package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.constant.Recommendation;

import javax.validation.constraints.NotNull;

/**
 * Covers the data relevant to heart disease predictions
 */
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartData extends HealthData{

    @NotNull
    private Double bloodPressure;
    @NotNull
    private Double cholesterol;
    @NotNull
    private Boolean priorHeartDisease;
    @NotNull
    private Boolean familyHeartDiseaseHistory;
    @NotNull
    private Float heartPrediction;
    @NotNull
    private Recommendation heartRecommendation;
}
