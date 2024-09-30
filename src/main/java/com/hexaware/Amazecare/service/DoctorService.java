package com.hexaware.Amazecare.service;

import com.hexaware.Amazecare.entities.Doctor;
import com.hexaware.Amazecare.exception.DoctorNotFoundException;
import com.hexaware.Amazecare.repo.DoctorRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepo doctorRepo;

    @Autowired
    public DoctorService(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepo.findAll();
    }

    public List<Doctor> findDoctorsBySpecialization(String specialization) {

        return doctorRepo.findDoctorsBySpecialization(specialization);
    }

    public List<Doctor> findDoctorsContainingFirstName(String firstName) {
        return doctorRepo.findDoctorsContainingFirstName(firstName);
    }

    public Optional<Doctor> findDoctorById(Long id) throws DoctorNotFoundException {
        Optional<Doctor> doctorOptional = doctorRepo.findDoctorById(id);
        if (doctorOptional.isPresent()) return doctorOptional;
        throw new DoctorNotFoundException("Doctor not found with id " + id);
    }

    public Doctor findDoctorByEmail(String email) throws Exception{
        return doctorRepo.findDoctorByEmail(email).orElseThrow();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Doctor updateDoctor(Doctor newDoctor) {
        return doctorRepo.save(newDoctor);
    }

    @Transactional
    public void deleteDoctor(Long doctorId) {
        var doctor = doctorRepo.findDoctorById(doctorId);
        if (doctor.isPresent()) {
            doctorRepo.delete(doctor.get());
        } else {
            throw new DoctorNotFoundException("Doctor not found with id " + doctorId);
        }
    }

}
