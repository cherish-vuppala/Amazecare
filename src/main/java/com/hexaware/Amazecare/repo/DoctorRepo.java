package com.hexaware.Amazecare.repo;

import com.hexaware.Amazecare.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    List<Doctor> findDoctorsBySpecialization(String specialization);

    List<Doctor> findDoctorsByFirstName(String name);

    void deleteDoctorByDoctorId(Long Id);
}
