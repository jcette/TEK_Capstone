package org.johnchoi.insuranceoptimizer.models;

import java.util.Arrays;

public enum SubstanceUse {
    NONE, LOW, MEDIUM, HIGH, NO_DATA;

    public static SubstanceUse getSunStanceUse(String substanceUse) {
        return  Arrays.stream( SubstanceUse.values()).filter(
                (t) -> t.name().equalsIgnoreCase(substanceUse)).findFirst().orElse(SubstanceUse.NO_DATA);
    }
}
