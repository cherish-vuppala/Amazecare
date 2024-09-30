package com.hexaware.Amazecare.controller;


import com.hexaware.Amazecare.entities.Patient;
import com.hexaware.Amazecare.exception.PatientNotFoundException;
import com.hexaware.Amazecare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        var patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, OK);
    }


    @GetMapping("/patients/name/{name}")
    public ResponseEntity<List<Patient>> getPatientsByName(@PathVariable String name) {
        var patients = patientService.getPatientsByName(name);
        return new ResponseEntity<>(patients, OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("patientId") Long patientId) throws PatientNotFoundException {
        Optional<Patient> patient = patientService.getPatientById(patientId);
        return patient.map(value -> new ResponseEntity<>(value, OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @PostMapping("/patient/save")
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) throws Exception{
        if (patientService.getPatientByEmail(patient.getPersonalDetails().getEmail()) == null) {
            return new ResponseEntity<>(patientService.savePatient(patient), OK);
        }
        throw new Exception("Patient already exists");
    }

    @PutMapping("/patient/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient newPatient) {
        return new ResponseEntity<>(patientService.updatePatient(newPatient), OK);
    }

    @DeleteMapping("/patient/delete/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable("patientId") Long patientId) throws PatientNotFoundException {
        patientService.deletePatient(patientId);
        return new ResponseEntity<>(OK);
    }
}
