package com.hexaware.Amazecare.repo;

import com.hexaware.Amazecare.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    Optional<Patient> findPatientById(Long id);

    @Query("SELECT p FROM Patient p WHERE p.personalDetails.email = :email")
    Patient findPatientByEmail(@Param("email") String email);
    // JPQL query
    // Works on the entity class that we have created using @Entity annotation but not on sql table
    // Demonstration of named query parameters
    @Query("SELECT p FROM Patient p WHERE p.personalDetails.firstName LIKE %:firstName%")
    List<Patient> findPatientsContainingFirstName(@Param("firstName") String firstName);

//    @Query(value = "SELECT * FROM patients WHERE first_name LIKE %:name%", nativeQuery = true)
//    List<Patient> findByFirstNameContainingName(@Param("name") String name);
    @Query(value = "SELECT * FROM patients WHERE first_name LIKE %:name%", nativeQuery = true)
    List<Patient> findByFirstNameContainingNameNative(@Param("name") String name);
}
