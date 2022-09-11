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

@RestController
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/caravan")
public class CaravanController {

    public CaravanService caravanService;

    @GetMapping("/visitors/all")
    public ResponseEntity<List<CaravanDTO>> getAllCaravan(){
        List<CaravanDTO>caravans=caravanService.fetchAllCaravans();
        return new ResponseEntity<>(caravans,HttpStatus.OK);
    }

    @PostMapping("/admin/{imageId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>>addCaravan(@PathVariable String imageId, @Valid @RequestBody Caravan caravan){
        caravanService.add(caravan,imageId);

        Map<String,Boolean>map=new HashMap<>();
        map.put("Caravan added successfully", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
