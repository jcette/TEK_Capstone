package org.johnchoi.insuranceoptimizer.models.constant;

import java.util.Arrays;
import java.util.Random;

public enum Recommendation {
    DECREASE, MAINTAIN, INCREASE, NO_DATA;

    private static final Random recs = new Random();

    public static Recommendation randomRecommendation() {
        Recommendation[] predictions = values();
        return predictions[recs.nextInt(predictions.length)];
    }

    public Recommendation getRecommendation( String value){
        return  Arrays.stream( Recommendation.values()).filter(
                (t) -> t.name().equalsIgnoreCase(value)).findFirst().orElse(Recommendation.NO_DATA);
    }
}
