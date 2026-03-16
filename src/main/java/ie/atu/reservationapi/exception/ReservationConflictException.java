package ie.atu.reservationapi.exception;

public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException(String timeSlotAlreadyBooked){
        super(timeSlotAlreadyBooked);
    }
}
