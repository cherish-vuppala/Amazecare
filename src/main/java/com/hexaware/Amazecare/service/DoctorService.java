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

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {

        return doctorRepo.findDoctorsBySpecialization(specialization);
    }

    public List<Doctor> getDoctorsByName(String name) {
        return doctorRepo.findDoctorsByFirstName(name);
    }

    public Optional<Doctor> getDoctorById(Long id) throws DoctorNotFoundException {
        Optional<Doctor> doctorOptional = doctorRepo.findById(id);
        if (doctorOptional.isPresent()) return doctorOptional;
        throw new DoctorNotFoundException("Doctor not found with id " + id);
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Doctor updateDoctor(Doctor newDoctor) {
        return doctorRepo.save(newDoctor);
    }

    @Transactional
    public void deleteDoctor(Long id) throws DoctorNotFoundException{
        var doctorOptional = doctorRepo.findById(id);
        if (doctorOptional.isPresent()) {
            var doctor = doctorOptional.get();
            doctorRepo.deleteDoctorByDoctorId(doctor.getDoctorId());
        }
        throw new DoctorNotFoundException("Doctor not found with id " + id);
    }

}
