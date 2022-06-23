package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.constant.Premium;
import org.johnchoi.insuranceoptimizer.models.constant.Tier;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceData {

    @NotNull
    private Tier tier;
    @NotNull
    private Premium premium;

}

