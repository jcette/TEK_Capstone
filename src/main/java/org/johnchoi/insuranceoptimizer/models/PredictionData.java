package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.constant.Recommendation;

import javax.validation.constraints.NotNull;

/**
 * Data class that encompasses the cancer, heart predictions and the cancer/heart/overall recommendations for each individual row in the database
 */
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
