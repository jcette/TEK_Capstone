package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientData {

    User Client;

    List<CancerData> cancerData;

    List<HeartData> heartData;

    List<PredictionData> predictionData;

    List<SensitiveData> sensitiveData;

    List<FinanceData> financeData;

    List<GroupPrediction> groupPredictions;
}
