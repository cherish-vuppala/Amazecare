package com.hexaware.Amazecare.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "appointment_date", nullable = false)
    @FutureOrPresent
    private LocalDate date;

    @Column(nullable = false) // Ensure start time is not null
    private LocalTime startTime;

    @Column(nullable = false) // Ensure end time is not null
    private LocalTime endTime;

    @ManyToOne // Many appointments can belong to one patient
    @JoinColumn(name = "patient_id") // Foreign key in the Appointment table
    private Patient patient;

    @ManyToOne // Many appointments can belong to one doctor
    @JoinColumn(name = "doctor_id") // Foreign key in the Appointment table
    private Doctor doctor;

    @Column(nullable = false) // Mark availability as not null
    private boolean isAvailable;

    @Column(nullable = false)
    private String reason;


    @Column()
    private boolean isExpired = false;
}
