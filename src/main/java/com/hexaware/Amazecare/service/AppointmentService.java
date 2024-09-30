package com.hexaware.Amazecare.service;

import com.hexaware.Amazecare.entities.Appointment;
import com.hexaware.Amazecare.entities.Doctor;
import com.hexaware.Amazecare.entities.Patient;
import com.hexaware.Amazecare.repo.AppointmentRepo;
import com.hexaware.Amazecare.repo.DoctorRepo;
import com.hexaware.Amazecare.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private PatientRepo patientRepo;

    // Working hours: 10 AM to 4 PM
    private static final LocalTime START_TIME = LocalTime.of(10, 0);
    private static final LocalTime END_TIME = LocalTime.of(16, 0);
    private static final int MAX_APPOINTMENTS_PER_SLOT = 4;
    private static final int APPOINTMENT_DURATION_MINUTES = 15; // Appointment duration

    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Transactional
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDate date, LocalTime time, String reason) {
        // Step 1: Check if time is within working hours
        if (!isWithinWorkingHours(time)) {
            throw new IllegalArgumentException("Appointment time must be within working hours (10 AM to 4 PM).");
        }

        // Step 2: Check if there are available slots for this doctor on the selected date and time
        if (!isSlotAvailable(doctorId, date, time)) {
            throw new IllegalArgumentException("No available slots for this time.");
        }

        // Step 3: Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setDate(date);
        appointment.setStartTime(time);
        appointment.setEndTime(time.plusMinutes(APPOINTMENT_DURATION_MINUTES));
        appointment.setReason(reason);
        appointment.setDoctor(doctorRepo.findDoctorById(doctorId).orElseThrow());
        appointment.setPatient(patientRepo.findPatientById(patientId).orElseThrow());

        return appointmentRepo.save(appointment);
    }

    // Check if the appointment time falls within working hours (10 AM to 4 PM)
    private boolean isWithinWorkingHours(LocalTime time) {
        return !time.isBefore(START_TIME) && !time.isAfter(END_TIME.minusMinutes(APPOINTMENT_DURATION_MINUTES));
    }

    // Check if there are available slots for the selected date and time (4 per slot)
    private boolean isSlotAvailable(Long doctorId, LocalDate date, LocalTime time) {
        LocalTime startOfSlot = time.withMinute((time.getMinute() / APPOINTMENT_DURATION_MINUTES) * APPOINTMENT_DURATION_MINUTES);

        // Fetch appointments for the doctor on the selected date and at the specific 15-minute slot
        List<Appointment> appointments = appointmentRepo
                .findAppointmentsByDoctorIdAndDateAndStartTime(doctorId, date, startOfSlot);

        // Check if less than 4 appointments are booked for that slot
        return appointments.size() < MAX_APPOINTMENTS_PER_SLOT;
    }

    // Show all booked appointments for a specific doctor or patient
    public List<Appointment> showBookedAppointments(Long doctorId, Long patientId) {
        if (doctorId != null) {
            return appointmentRepo.findAppointmentsByDoctorId(doctorId);
        } else if (patientId != null) {
            return appointmentRepo.findAppointmentsByPatientId(patientId);
        } else {
            throw new IllegalArgumentException("Either doctorId or patientId must be provided.");
        }
    }

    // Show available appointment slots for a specific doctor on a specific date
    public List<LocalTime> showAvailableAppointments(Long doctorId, LocalDate date) {
        List<LocalTime> availableSlots = new ArrayList<>();
        List<Appointment> bookedAppointments = appointmentRepo.findAppointmentsByDoctorIdAndDate(doctorId, date);

        for (LocalTime currentSlot = START_TIME; currentSlot.isBefore(END_TIME); currentSlot = currentSlot.plusMinutes(APPOINTMENT_DURATION_MINUTES)) {
            LocalTime finalCurrentSlot = currentSlot;
            long bookedCount = bookedAppointments.stream()
                    .filter(appointment -> appointment.getStartTime().equals(finalCurrentSlot))
                    .count();

            if (bookedCount < MAX_APPOINTMENTS_PER_SLOT) {
                availableSlots.add(currentSlot);  // Add slot if available
            }
        }

        return availableSlots;
    }

    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void updateExpiredAppointments() {
        LocalTime now = LocalTime.now();
        List<Appointment> appointments = appointmentRepo.findAllByIsExpiredFalseAndEndTimeBefore(now);

        for (Appointment appointment : appointments) {
            appointment.setExpired(true);
            appointmentRepo.save(appointment);
        }
    }
}
