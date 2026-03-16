package ie.atu.reservationapi.repository;

import ie.atu.reservationapi.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

//                                            <Date type of entity, data type of primary key>
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReservationDate(LocalDate reservationDate);

    List<Reservation> findByEquipmentTag(String equipmentTag);
}
