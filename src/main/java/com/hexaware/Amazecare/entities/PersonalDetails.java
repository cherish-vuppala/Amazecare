package com.hexaware.Amazecare.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDetails {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, length = 15) // Adjust length based on phone number format
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    @Email
    private String email; // Assuming email is unique
}
