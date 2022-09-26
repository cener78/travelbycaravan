package com.newproject.travelbycaravan.controller;



import com.newproject.travelbycaravan.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/excel")
public class ExcelController {

    ExcelService excelService;

    @GetMapping("/download/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getUserFile() throws IOException {
        String fileName="customers.xlsx";
        InputStreamResource file=new InputStreamResource(excelService.loadUser());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vmd.ms-excel")).body(file);
    }


}
