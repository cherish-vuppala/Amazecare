package com.hexaware.Amazecare.repo;

import com.hexaware.Amazecare.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    List<Doctor> findDoctorsBySpecialization(String specialization);

    @Query("SELECT d FROM Doctor d WHERE d.personalDetails.firstName LIKE %:firstName%")
    List<Doctor> findDoctorsContainingFirstName(@Param("firstName") String firstName);

    Optional<Doctor> findDoctorById(Long id);

    @Query("SELECT d FROM Doctor d WHERE d.personalDetails.email = :email")
    Optional<Doctor> findDoctorByEmail(@Param("email") String email);
}
