package org.johnchoi.insuranceoptimizer.models;

import java.util.Arrays;

public enum Diet {

    UNHEALTHY, AVERAGE, HEALTHY, NO_DATA;

    public static Diet getDiet( String name){
        return  Arrays.stream( Diet.values()).filter(
                (t) -> t.name().equalsIgnoreCase(name)).findFirst().orElse(Diet.NO_DATA);
    }
}
