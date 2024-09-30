package com.hexaware.Amazecare.service;


import com.hexaware.Amazecare.entities.Patient;
import com.hexaware.Amazecare.exception.PatientNotFoundException;
import com.hexaware.Amazecare.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepo patientRepo;

    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public Patient savePatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Optional<Patient> getPatientById(Long id) throws PatientNotFoundException {
        var patientOptional = patientRepo.findPatientById(id);
        if (patientOptional.isPresent()) return patientOptional;
        else {
            throw new PatientNotFoundException("Patient not found with id " + id);
        }
    }

    public List<Patient> getPatientsByName(String name) {
        var patients = patientRepo.findPatientsContainingFirstName(name);
        return patients;
    }

    public Patient getPatientByEmail(String email) {
        return patientRepo.findPatientByEmail(email);
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient updatePatient(Patient newPatient) {
        return patientRepo.save(newPatient);
    }

    public void deletePatient(Long id) throws PatientNotFoundException{
        var patientOptional = patientRepo.findPatientById(id);
        if (patientOptional.isPresent()) {
            patientRepo.delete(patientOptional.get());
        } else {
            throw new PatientNotFoundException("Patient not found with id " + id);
        }
    }


}
