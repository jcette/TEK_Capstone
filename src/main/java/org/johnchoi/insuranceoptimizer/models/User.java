package org.johnchoi.insuranceoptimizer.models;

import lombok.Data;
import lombok.ToString;
import org.johnchoi.insuranceoptimizer.models.constant.UserRoles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * data class that stores user login and registration information
 */
@Data
@ToString
public class User {

    @NotNull
    private String name;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotEmpty
    private String phoneNumber;
    @NotBlank
    private String email;
    @NotNull
    private UserRoles userRole;

}
