package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionData {

    @NotNull
    private Float cancerPrediction;
    @NotNull
    private Float heartPrediction;
    @NotNull
    private Recommendation cancerRecommendation;
    @NotNull
    private Recommendation heartRecommendation;
    @NotNull
    private Recommendation overallRecommendation;
}
