package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Client data encapsulates all data for a particular client user, including health, cancer, heart, prediction, sensitive, finance, and group
 */
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
