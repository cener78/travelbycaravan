package com.newproject.travelbycaravan.repository;


import com.newproject.travelbycaravan.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
