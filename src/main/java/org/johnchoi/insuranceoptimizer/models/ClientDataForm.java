package org.johnchoi.insuranceoptimizer.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDataForm {

    Long dataId;

    SensitiveData sensitiveData;

    HealthData healthData;

}
