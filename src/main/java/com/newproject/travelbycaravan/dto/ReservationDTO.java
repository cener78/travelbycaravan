package com.newproject.travelbycaravan.dto;

import com.newproject.travelbycaravan.model.Reservation;
import com.newproject.travelbycaravan.model.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private CaravanDTO caravanId;

    private Long userId;

    private LocalDateTime pickUpTime;

    private LocalDateTime dropOffTime;

    private String pickUpLocation;

    private String dropOffLocation;

    private ReservationStatus status;

    private Double totalPrice;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.caravanId = new CaravanDTO(reservation.getCaravanId());
        this.userId = reservation.getUserId().getId();
        this.pickUpTime = reservation.getPickUpTime();
        this.dropOffTime = reservation.getDropOffTime();
        this.pickUpLocation = reservation.getPickUpLocation();
        this.dropOffLocation = reservation.getDropOffLocation();
        this.status = reservation.getStatus();
        this.totalPrice = reservation.getTotalPrice();
    }
}
