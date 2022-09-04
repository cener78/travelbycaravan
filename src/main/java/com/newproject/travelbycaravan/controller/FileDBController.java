package com.newproject.travelbycaravan.controller;


import com.newproject.travelbycaravan.domain.FileDB;
import com.newproject.travelbycaravan.service.FileDBService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@CrossOrigin("http://localhost:8081")
public class FileDBController {

    private final FileDBService fileDBService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            FileDB fileDB=fileDBService.store(file);
            Map<String,String>map=new HashMap<>();
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch(IOException e){
            Map<String, String>map=new HashMap<>();
            map.put("message","Could not upload the file: " + file.getOriginalFilename()+"!");

            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
        }

    }
}
