package com.hexaware.Amazecare.controller;

import com.hexaware.Amazecare.entities.Doctor;
import com.hexaware.Amazecare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        var doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, OK);
    }

    @GetMapping("/doctors/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        var doctors = doctorService.getDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(doctors, OK);
    }

    @GetMapping("/doctors/{name}")
    public ResponseEntity<List<Doctor>> getDoctorsByName(@PathVariable String name) {
        var doctors = doctorService.getDoctorsByName(name);
        return new ResponseEntity<>(doctors, OK);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        var doctor = doctorService.getDoctorById(id);
        if (doctor.isPresent()) return new ResponseEntity<>(doctor.get(), FOUND);
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/doctor/save")
    public ResponseEntity<Doctor> saveDoctor(Doctor doctor) {
         return new ResponseEntity<>(doctorService.saveDoctor(doctor), OK);
    }

    @PutMapping("/doctor/update")
    public ResponseEntity<Doctor> updateDoctor(Doctor newDoctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(newDoctor), OK);
    }

    @DeleteMapping("/doctor/delete/{id}")
    public ResponseEntity<?> deleteDoctor(Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(OK);
    }

}
