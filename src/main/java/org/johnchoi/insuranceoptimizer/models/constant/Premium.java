package org.johnchoi.insuranceoptimizer.models.constant;

import java.util.Arrays;

public enum Premium {

    LOW, MEDIUM, HIGH, NO_DATA;

    public Premium getPremium( String name){
        return  Arrays.stream( Premium.values()).filter(
                (t) -> t.name().equalsIgnoreCase(name)).findFirst().orElse(Premium.NO_DATA);
    }

}
