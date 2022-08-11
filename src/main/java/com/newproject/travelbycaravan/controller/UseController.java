package com.newproject.travelbycaravan.controller;


import com.newproject.travelbycaravan.domain.User;
import com.newproject.travelbycaravan.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping()
@AllArgsConstructor
public class UseController {
    public UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>>registerUser( @Valid @RequestBody User user){
        userService.register(user);

         Map<String,Boolean>map=new HashMap<>();
         map.put("User is registered successfully", true);
         return new ResponseEntity<>(map, HttpStatus.CREATED);

    }



}
