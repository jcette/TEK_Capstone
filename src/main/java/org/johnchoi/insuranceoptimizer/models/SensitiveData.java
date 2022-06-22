package org.johnchoi.insuranceoptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveData {

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private String gender;
}
