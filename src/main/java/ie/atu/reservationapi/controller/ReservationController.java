package ie.atu.reservationapi.controller;

import ie.atu.reservationapi.model.Reservation;
import ie.atu.reservationapi.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    // Create object
    @PostMapping
    public ResponseEntity<Reservation> create(@Valid @RequestBody Reservation reservation){
        Reservation saved = reservationService.addReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    //Get by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> getReservationByDate(@PathVariable LocalDate date){
        return ResponseEntity.ok(reservationService.getReservationByDate(date));
    }

    //Get by tag
    @GetMapping("/equipment/{tag}")
    public ResponseEntity<List<Reservation>> getReservationByTag(@PathVariable String tag){
        return ResponseEntity.ok(reservationService.getReservationByTag(tag));
    }
}
