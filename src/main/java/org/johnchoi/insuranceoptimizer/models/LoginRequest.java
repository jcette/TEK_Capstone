package org.johnchoi.insuranceoptimizer.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull
    String email;
    @NotNull
    String password;

}
