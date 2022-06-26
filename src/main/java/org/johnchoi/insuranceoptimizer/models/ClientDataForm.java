package org.johnchoi.insuranceoptimizer.models;

import lombok.Builder;
import lombok.Data;

/** The form classes take input from the front-end to modify things on the back-end
 * ClientDataForm takes edit/delete commands from the front end admin page
 */
@Data
@Builder
public class ClientDataForm {

    Long dataId;

    SensitiveData sensitiveData;

    HealthData healthData;

}
