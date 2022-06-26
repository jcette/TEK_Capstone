package org.johnchoi.insuranceoptimizer.models;

import lombok.Data;

/**
 * This form class takes and saves the input from the admin page (selected client email, checkbox to show data, and csv upload)
 */
@Data
public class AdminForm {

    String clientEmail;

    boolean showData;
}
