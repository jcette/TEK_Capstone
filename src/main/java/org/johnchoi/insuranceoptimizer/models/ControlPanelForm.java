package org.johnchoi.insuranceoptimizer.models;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * An instance of this class represents the client page inputs: the client can opt to see heart/cancer/both and individual/group/both. This ferries that preference to the backend and will cause the program to populate the desired tables
 */
@Data
@ToString
public class ControlPanelForm {

    @NotNull
    private String prediction;
    @NotNull
    private String population;

}
