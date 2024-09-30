package com.hexaware.Amazecare.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @Column(name = "user_id")
    private Long id;  // Shared primary key with User

    @OneToOne
    @MapsId  // This maps the Patient's ID to User's ID
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Embedded
    private PersonalDetails personalDetails;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
}
