package org.johnchoi.insuranceoptimizer.models.constant;

import java.util.Arrays;

public enum Smoking {

    NONE, LOW, MEDIUM, HIGH, NO_DATA;

    public static Smoking getSmoking(String smoking) {
        return  Arrays.stream( Smoking.values()).filter(
                (t) -> t.name().equalsIgnoreCase(smoking)).findFirst().orElse(Smoking.NO_DATA);
    }
}
