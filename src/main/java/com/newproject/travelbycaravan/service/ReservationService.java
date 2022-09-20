package com.newproject.travelbycaravan.service;


import com.newproject.travelbycaravan.model.Caravan;
import com.newproject.travelbycaravan.model.Reservation;
import com.newproject.travelbycaravan.model.User;
import com.newproject.travelbycaravan.model.enumeration.ReservationStatus;
import com.newproject.travelbycaravan.dto.ReservationDTO;
import com.newproject.travelbycaravan.exception.BadRequestException;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import com.newproject.travelbycaravan.repository.CaravanRepository;
import com.newproject.travelbycaravan.repository.ReservationRepository;
import com.newproject.travelbycaravan.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
// 3 yontemle yapila bilir 1. AllArgsConstructor kullaniriz
// 2. repository interface inden olusturdugumuz nesneyi Autowired yapariz
// 3. constructor olustururuz
public class ReservationService {



    //@Autowired Kullanirsak final olmaz
    private final ReservationRepository reservationRepository;

    private UserRepository userRepository;

    private CaravanRepository caravanRepository;

    private final static String USER_NOT_FOUND_MSG="User with Id %d not found";

    private final static String CARAVAN_NOT_FOUND_MSG="Caravan with Id %d not found";

    private static final String RESERVATION_NOT_FOUND_MSG = "Reservation with id %d not found";

   /* public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }*/


    public void addReservation(Reservation reservation, Long userId, Caravan caravanId) throws BadRequestException {

        boolean checkStatus=caravanAvailability(caravanId.getId(),reservation.getPickUpTime(),
                reservation.getDropOffTime());

        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG,  userId)));

        if(!checkStatus)
            reservation.setStatus(ReservationStatus.CREATED);
        else
            throw  new BadRequestException("Caravan is alredy reserved! Please choose another");

        reservation.setCaravanId(caravanId);
        reservation.setUserId(user);

        Double totalPrice=totalPrice(reservation.getPickUpTime(),reservation.getDropOffTime(),caravanId.getId());

        reservation.setTotalPrice(totalPrice);
        reservationRepository.save(reservation);


   }

    private Double totalPrice(LocalDateTime pickUpTime, LocalDateTime dropOffTime, Long caravanId) {
       Caravan caravan=caravanRepository.findById(caravanId).orElseThrow(()->
               new ResourceNotFoundException(String.format(CARAVAN_NOT_FOUND_MSG,caravanId)));
      Long hours =(new Reservation()).getTotalHours(pickUpTime,dropOffTime);
    Long days;
    if(hours%24==0)
        days=hours/24;
    else days=hours/24+1;

    return caravan.getPricePerDay()*days;

    }

    private boolean caravanAvailability(Long caravanId, LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
        List<Reservation> checkStatus=reservationRepository.checkStatus(caravanId,pickUpTime,dropOffTime,
                ReservationStatus.DONE,ReservationStatus.CANCELED);

        return checkStatus.size()>0;
    }

    public List<ReservationDTO> findAllByUserId(Long userId) {
        User user =userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG,userId)));
        return reservationRepository.findAllByUserId(user);
    }

    public ReservationDTO findByIdAndUserId(Long id, Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        return reservationRepository.findByIdAndUserId(id,user).orElseThrow(()->
                new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG, id)));
    }

    public List<ReservationDTO> fetchAllReservations() {

        return reservationRepository.findAllBy();
    }

    public ReservationDTO findById(Long id) {
        return reservationRepository.findByIdOrderById(id).orElseThrow(()->
        new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG,id)));
    }

    public void updateReservation(Caravan caravanId, Long reservationId, Reservation reservation) throws BadRequestException {
        boolean checkStatus=caravanAvailability(caravanId.getId(),reservation.getPickUpTime(),reservation.getDropOffTime());

        Reservation reservationExist=reservationRepository.findById(reservationId).orElseThrow(()->
                new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG,reservationId)));
            if(reservation.getPickUpTime().compareTo(reservationExist.getPickUpTime())==0&&
            reservation.getDropOffTime().compareTo(reservationExist.getDropOffTime())==0&&
            caravanId.getId().equals(reservationExist.getCaravanId().getId())){

                reservationExist.setStatus(reservation.getStatus());

            }else if(checkStatus)
                throw new BadRequestException("Caravan is already reserved, please select another");

            Double totalPrice=totalPrice(reservation.getPickUpTime(),reservation.getDropOffTime(),caravanId.getId());

            reservationExist.setTotalPrice(totalPrice);
            reservationExist.setCaravanId(caravanId);
            reservationExist.setPickUpTime(reservation.getPickUpTime());
            reservationExist.setDropOffTime(reservation.getDropOffTime());
            reservationExist.setPickUpLocation(reservation.getPickUpLocation());
            reservationExist.setDropOffLocation(reservation.getDropOffLocation());

            reservationRepository.save(reservationExist);

    }
}
