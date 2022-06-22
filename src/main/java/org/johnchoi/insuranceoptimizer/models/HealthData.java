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
public class HealthData {

    @NotNull
    private Long id;

    @NotNull
    private Integer age;
    @NotNull
    private Double weight;
    @NotNull
    private Double height;
    @NotNull
    private Exercise exercise;
    @NotNull
    private Smoking smoking;
    @NotNull
    private SubstanceUse substanceUse;
    @NotNull
    private Diet diet;
    @NotNull
    private Recommendation overallRecommendation;


}
