package org.johnchoi.insuranceoptimizer.models;

import java.util.Arrays;

public enum Exercise {
    NONE, LOW, REGULAR, HIGH, NO_DATA;

    public static Exercise getExercise(String value){
        return  Arrays.stream( Exercise.values()).filter(
                (t) -> t.name().equalsIgnoreCase(value)).findFirst().orElse(Exercise.NO_DATA);
    }
}
