package com.hexaware.Amazecare.controller;

import com.hexaware.Amazecare.entities.Doctor;
import com.hexaware.Amazecare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        var doctors = doctorService.findAllDoctors();
        return new ResponseEntity<>(doctors, OK);
    }

    @GetMapping("/doctors/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        var doctors = doctorService.findDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(doctors, OK);
    }

    @GetMapping("/doctors/name/{name}")
    public ResponseEntity<List<Doctor>> getDoctorsByName(@PathVariable String name) {
        var doctors = doctorService.findDoctorsContainingFirstName(name);
        return new ResponseEntity<>(doctors, OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("doctorId") Long doctorId) {
        Optional<Doctor> doctor = doctorService.findDoctorById(doctorId);
        return doctor.map(value -> new ResponseEntity<>(value, OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @PostMapping("/doctor/save")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) throws Exception{
        if (doctorService.findDoctorByEmail(doctor.getPersonalDetails().getEmail()) == null)
            return new ResponseEntity<>(doctorService.saveDoctor(doctor), CREATED);
       throw new Exception("Doctor already exists");
    }

    @PutMapping("/doctor/update")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor newDoctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(newDoctor), OK);
    }

    @DeleteMapping("/doctor/delete/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("doctorId") Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return new ResponseEntity<>(OK);
    }
}
