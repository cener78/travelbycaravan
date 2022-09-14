package com.newproject.travelbycaravan.controller;

import com.newproject.travelbycaravan.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@AllArgsConstructor
@RequestMapping("/reservations")
@RestController
@Produces(MediaType.APPLICATION_JSON)
public class ReservationController {

    public ReservationService reservationService;

}
