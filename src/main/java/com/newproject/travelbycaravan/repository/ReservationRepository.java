package com.newproject.travelbycaravan.repository;


import com.newproject.travelbycaravan.model.Reservation;
import com.newproject.travelbycaravan.model.User;
import com.newproject.travelbycaravan.model.enumeration.ReservationStatus;
import com.newproject.travelbycaravan.dto.ReservationDTO;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation,Long> {


   @Query("SELECT r FROM Reservation r " +
           "LEFT JOIN fetch  r.caravanId cd " +
           "LEFT JOIN fetch  cd.image img " +
           "LEFT JOIN fetch  r.userId uid " +
           "WHERE (cd.id=?1 and r.status <> ?4 and r.status<> ?5 and " +
           "?2 BETWEEN r.pickUpTime and r.dropOffTime) or  " +
           "(cd.id=?1 and r.status <>?4 and r.status <> ?5 and " +
           "?3 BETWEEN r.pickUpTime and r.dropOffTime)")
    List<Reservation> checkStatus(Long caravanId,
                                  LocalDateTime pickUpTime,
                                  LocalDateTime dropOffTime,
                                  ReservationStatus done,
                                  ReservationStatus canceled);

   //@Query(" SELECT new com.newproject.travelbycaravan.dto.ReservationDTO(r) From Reservation  r WHERE r.userId.id=?1")
   List<ReservationDTO>findAllByUserId(User userId);


   //@Query("SELECT new com.newproject.travelbycaravan.dto.ReservationDTO(r) FROM Reservation r where r.id=?1 and r.userId.id=?2 ")
   Optional<ReservationDTO> findByIdAndUserId(Long id, User user) throws ResourceNotFoundException;

   //@Query("SELECT new com.newproject.travelbycaravan.dto.ReservationDTO(r) FROM Reservation r")
   List<ReservationDTO> findAllBy();

   Optional<ReservationDTO>findByIdOrderById(Long id) throws ResourceNotFoundException;
}
