package org.johnchoi.insuranceoptimizer.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.johnchoi.insuranceoptimizer.models.constant.UserRoles;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name="Users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Name")
    private String name;
    @Column(name="Password")
    private String password;
    @Column(name="Email", unique = true)
    private String email;
    @Column(name="Phone_Number")
    private String phoneNumber;
    @Column(name="User_Role")
    private UserRoles userRole;

}
