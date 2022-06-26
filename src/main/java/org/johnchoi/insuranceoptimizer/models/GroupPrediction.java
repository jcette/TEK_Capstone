package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * group prediction encapsulates the group-level predictions and recommendations, which are averages of the population's individual predictions and recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupPrediction {
    @NotNull
    private Double averageRisk;
    @NotNull
    private Double decrease;
    @NotNull
    private Double maintain;
    @NotNull
    private Double increase;
    @NotNull
    private String type;
}
