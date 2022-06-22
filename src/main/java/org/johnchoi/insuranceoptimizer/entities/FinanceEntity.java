package org.johnchoi.insuranceoptimizer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.Premium;
import org.johnchoi.insuranceoptimizer.models.Tier;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name="Financial_Information")
@Entity
public class FinanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Insurance_Tier")
    private Tier tier;
    @Column(name="Monthly_Premium")
    private Premium premium;


}
