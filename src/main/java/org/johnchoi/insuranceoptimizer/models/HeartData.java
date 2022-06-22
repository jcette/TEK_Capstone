package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
