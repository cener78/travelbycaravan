package com.newproject.travelbycaravan.controller;


import com.newproject.travelbycaravan.domain.Caravan;
import com.newproject.travelbycaravan.dto.CaravanDTO;
import com.newproject.travelbycaravan.service.CaravanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/caravan")
public class CaravanController {

    public CaravanService caravanService;

    @GetMapping("/visitors/{id}")
    public ResponseEntity<CaravanDTO> getCaravanById(@PathVariable Long id){
        CaravanDTO caravan=caravanService.findById(id);
        return new ResponseEntity<>(caravan, HttpStatus.OK);
    }

    @GetMapping("/visitors/all")
    public ResponseEntity<List<CaravanDTO>> getAllCaravan(){
        List<CaravanDTO>caravans=caravanService.fetchAllCaravans();
        return new ResponseEntity<>(caravans,HttpStatus.OK);
    }

    @PostMapping("/admin/{id}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> addCaravan(@PathVariable String id,
                                                       @Valid @RequestBody Caravan caravan) {
        caravanService.add(caravan, id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Caravan added successfully!", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> updateCaravan(@RequestParam("id") Long id,
                                                          @RequestParam("imageId") String imageId,
                                                          @Valid @RequestBody Caravan caravan) {
        caravanService.updateCaravan(id,imageId, caravan);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> deleteCaravan(@PathVariable Long id){
        caravanService.removeCaravanById(id);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
