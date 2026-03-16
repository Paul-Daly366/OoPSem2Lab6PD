package ie.atu.reservationapi.service;

import ie.atu.reservationapi.exception.ReservationConflictException;
import ie.atu.reservationapi.exception.ReservationNotFoundException;
import ie.atu.reservationapi.model.Reservation;
import ie.atu.reservationapi.repository.ReservationRepo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private List<Reservation> reservations;
    private final ReservationRepo reservationRepo;

    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    // Create
    public Reservation addReservation(@Valid Reservation reservation){

        reservations = reservationRepo.findAll();

        // Check for time conflicts
        for (Reservation existing : reservations){
            // Equipment & Date
            if(existing.getEquipmentTag().equals(reservation.getEquipmentTag()) &&
            existing.getReservationDate().equals(reservation.getReservationDate())){
                int existingStart = existing.getStartHour();
                int existingEnd = existingStart + existing.getDurationHours();

                int newStart = reservation.getStartHour();
                int newEnd = newStart + reservation.getDurationHours();

                //Check if overlap exists
                if(existingStart < newEnd && newStart < existingEnd){
                    throw new ReservationConflictException("Time slot already booked");
                }
            }
        }

        reservationRepo.save(reservation);
        return reservation;
    }

    public List<Reservation> getAllReservations(){
        return reservationRepo.findAll();
    }

    public Reservation getReservationById(Long id){
        return reservationRepo.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    public List<Reservation> getReservationByDate(LocalDate reservationDate){
        return reservationRepo.findByReservationDate(reservationDate);
    }

    public List<Reservation> getReservationByTag(String equipmentTag){
        return reservationRepo.findByEquipmentTag(equipmentTag);
    }
}
