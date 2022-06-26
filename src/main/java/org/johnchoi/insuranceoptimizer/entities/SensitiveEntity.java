package org.johnchoi.insuranceoptimizer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

/**
 * Represents HIPAA sensitive table in backend database. To-do: may have to make this less accessible
 */
@Data
@NoArgsConstructor
@Table(name="HIPAA_Sensitive_Information")
@Entity
public class SensitiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Name")
    private String name;
    @Column(name="DOB")
    private Date dateOfBirth;
    @Column(name="Email")
    private String email;
    @Column(name="Phone_Number")
    private String phoneNumber;
    @Column(name="Gender")
    private String gender;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="Client", referencedColumnName = "id")
    private UserEntity client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Health", referencedColumnName = "id")
    private HealthEntity health;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Finance", referencedColumnName = "id")
    private FinanceEntity finance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Prediction", referencedColumnName = "id")
    private PredictionEntity prediction;
}
