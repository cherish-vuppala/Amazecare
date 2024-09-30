package com.hexaware.Amazecare.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @Column(name = "user_id")
    private Long id;  // Shared primary key with User

    @OneToOne
    @MapsId  // This maps the Doctor's ID to User's ID
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Embedded
    private PersonalDetails personalDetails;

    @Column(nullable = false)
    private String specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

}
