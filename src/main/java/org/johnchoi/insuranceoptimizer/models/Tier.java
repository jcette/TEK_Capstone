package org.johnchoi.insuranceoptimizer.models;

import java.util.Arrays;

public enum Tier {

    BRONZE, SILVER, GOLD, NO_DATA;

    public Tier getExercise( String value){
        return  Arrays.stream( Tier.values()).filter(
                (t) -> t.name().equalsIgnoreCase(value)).findFirst().orElse(Tier.NO_DATA);
    }

}
