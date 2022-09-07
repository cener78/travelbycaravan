package com.newproject.travelbycaravan.controller;


import com.newproject.travelbycaravan.domain.Caravan;
import com.newproject.travelbycaravan.service.CaravanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/caravan")
public class CaravanController {

    public CaravanService caravanService;

    @PostMapping("/admin/{imageId}/add")
    public ResponseEntity<Map<String,Boolean>>addCaravan(@PathVariable String imageId, @Valid @RequestBody Caravan caravan){
        caravanService.add(caravan,imageId);

        Map<String,Boolean>map=new HashMap<>();
        map.put("Caravan added successfully", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
