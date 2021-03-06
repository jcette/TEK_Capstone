package org.johnchoi.insuranceoptimizer.models;

import lombok.*;
import org.johnchoi.insuranceoptimizer.models.constant.Recommendation;

import javax.validation.constraints.NotNull;

/**
 * Cancerdata encapsulates the portion of client data that would be specifically used to predict cancer outcomes
 */
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CancerData extends HealthData {

    @NotNull
    private Boolean priorCancer;
    @NotNull
    private Boolean familyCancerHistory;
    @NotNull
    private Float cancerPrediction;
    @NotNull
    private Recommendation cancerRecommendation;

}
