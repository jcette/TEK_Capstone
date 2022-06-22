package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
