package com.newproject.travelbycaravan.service;


import com.newproject.travelbycaravan.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
// 3 yontemle yapila bilir 1. AllArgsConstructor kullaniriz
// 2. repository interface inden olusturdugumuz nesneyi Autowired yapariz
// 3. constructor olustururuz
public class ReservationService {


    //@Autowired Kullanirsak final olmaz
    private final ReservationRepository reservationRepository;

   /* public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }*/


}
