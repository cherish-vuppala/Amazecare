package com.hexaware.Amazecare.controller;

import com.hexaware.Amazecare.entities.Appointment;
import com.hexaware.Amazecare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Endpoint to book an appointment
    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@RequestParam Long patientId,
                                                  @RequestParam Long doctorId,
                                                  @RequestParam LocalDate date,
                                                  @RequestParam LocalTime time, @RequestParam String reason) {
        try {
            appointmentService.bookAppointment(patientId, doctorId, date, time, reason);
            return ResponseEntity.ok("Appointment booked successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to show all booked appointments for a specific doctor or patient
    @GetMapping("/booked")
    public ResponseEntity<List<Appointment>> showBookedAppointments(@RequestParam(required = false) Long doctorId,
                                                                    @RequestParam(required = false) Long patientId) {
        try {
            List<Appointment> appointments = appointmentService.showBookedAppointments(doctorId, patientId);
            return ResponseEntity.ok(appointments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to show available appointment slots for a specific doctor on a specific date
    @GetMapping("/available")
    public ResponseEntity<List<LocalTime>> showAvailableAppointments(@RequestParam Long doctorId,
                                                                     @RequestParam LocalDate date) {
        try {
            List<LocalTime> availableSlots = appointmentService.showAvailableAppointments(doctorId, date);
            return ResponseEntity.ok(availableSlots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
