package com.newproject.travelbycaravan.controller;

import com.newproject.travelbycaravan.domain.Caravan;
import com.newproject.travelbycaravan.domain.Reservation;
import com.newproject.travelbycaravan.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RequestMapping("/reservations")
@RestController
@Produces(MediaType.APPLICATION_JSON)
public class ReservationController {

    public ReservationService reservationService;

    @PostMapping("add")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> makeReservation(HttpServletRequest request,
                                                               @RequestParam(value = "caravanId") Caravan caravanId,
                                                               @Valid @RequestBody Reservation reservation){

        Long userId=(Long)request.getAttribute("id");
        reservationService.addReervation(reservation,userId,caravanId);

        Map<String,Boolean>map=new HashMap<>();
        map.put("Reservation added", true);
        return new ResponseEntity<>(map,HttpStatus.CREATED);

    }

}
