package org.johnchoi.insuranceoptimizer.models;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
public class ControlPanelForm {

    @NotNull
    private String prediction;
    @NotNull
    private String population;

}
