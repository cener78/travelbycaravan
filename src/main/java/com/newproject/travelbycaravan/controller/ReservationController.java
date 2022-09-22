package com.newproject.travelbycaravan.controller;

import com.newproject.travelbycaravan.model.Caravan;
import com.newproject.travelbycaravan.model.Reservation;
import com.newproject.travelbycaravan.dto.ReservationDTO;
import com.newproject.travelbycaravan.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
        reservationService.addReservation(reservation,userId,caravanId);

        Map<String,Boolean>map=new HashMap<>();
        map.put("Reservation added", true);
        return new ResponseEntity<>(map,HttpStatus.CREATED);

    }

    @PostMapping("add/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> addReservation(@RequestParam(value = "userId") Long userId,
                                                              @RequestParam(value = "caravanId") Caravan caravanId,
                                                              @Valid @RequestBody Reservation reservation){

        reservationService.addReservation(reservation,userId,caravanId);
        Map<String,Boolean>map=new HashMap<>();
        map.put("Reservation added successfully",true);
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getUserReservationsById(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("id");
        List<ReservationDTO> reservation = reservationService.findAllByUserId(userId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<ReservationDTO>getUserReservationById(@PathVariable Long id, HttpServletRequest request){
       Long userId=(Long)request.getAttribute("id");
       ReservationDTO reservation=reservationService.findByIdAndUserId(id,userId);
       return new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<List<ReservationDTO>>getAllReservation(){
        List<ReservationDTO>reservations=reservationService.fetchAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationDTO>getReservationById(@PathVariable Long id){
        ReservationDTO reservation=reservationService.findById(id);
        return  new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @GetMapping("/admin/auth/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getAllUserReservations(@RequestParam(value = "userId") Long userId){

        List<ReservationDTO> reservations=reservationService.findAllByUserId(userId); // bu methodu daha once yazmistik serviste. iki yerde kullandik
                                                                                        // ilkinde id requestenyani token dan aldik burada admin kendi girdi.
        return new ResponseEntity<>(reservations, HttpStatus.OK);

    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> updateReservation(@RequestParam(value = "caravanId") Caravan caravanId,
                                                               @RequestParam(value = "reservationId")Long reservationId,
                                                               @Valid @RequestBody Reservation reservation){

        reservationService.updateReservation(caravanId,reservationId,reservation);

        Map<String,Boolean>map=new HashMap<>();
        map.put("Reservation is updated successfully",true);
        return new ResponseEntity<>(map,HttpStatus.OK);

    }

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> deleteReservation(@PathVariable Long id){
        reservationService.romoveById(id);

        Map<String, Boolean>map= new HashMap<>();
        map.put("Reservation removed",true);
        return new ResponseEntity<>(map,HttpStatus.OK);

    }

    @GetMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<Map<String,Object>> checkCaravanAvailability(@RequestParam(value = "caravanId") Long caravanId,
                                                                       @RequestParam(value = "pickUpDateTime")
                                                                       @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
                                                                       LocalDateTime pickUpTime,
                                                                       @RequestParam(value = "dropOffDateTime")
                                                                       @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
                                                                       LocalDateTime dropOffTime){



      boolean availability= reservationService.caravanAvailability(caravanId,pickUpTime,dropOffTime);

      Double totalPrice=reservationService.totalPrice(pickUpTime,dropOffTime,caravanId);

      Map<String,Object>map=new HashMap<>();
      map.put("isAvailable", !availability);
      map.put("totalPrice",totalPrice);
      return new ResponseEntity<>(map,HttpStatus.OK);

    }



}
