package com.hexaware.Amazecare.repo;

import com.hexaware.Amazecare.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findAppointmentsByDoctorIdAndDate(Long doctorId, LocalDate date);

    List<Appointment> findAppointmentsByPatientId(Long patientId);

    List<Appointment> findAppointmentsByDoctorId(Long doctorId);

    List<Appointment> findAllByIsExpiredFalseAndEndTimeBefore(LocalTime now);

    List<Appointment> findAppointmentsByDoctorIdAndDateAndStartTime(Long doctorId, LocalDate date, LocalTime startOfSlot);
}
