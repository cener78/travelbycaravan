package com.newproject.travelbycaravan.controller;


import com.newproject.travelbycaravan.model.FileDB;
import com.newproject.travelbycaravan.dto.FileDTO;
import com.newproject.travelbycaravan.service.FileDBService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@CrossOrigin("http://localhost:8081")
public class FileDBController {

    private final FileDBService fileDBService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileDB fileDB = fileDBService.store(file);
            Map<String, String> map = new HashMap<>();
            map.put("imageId", fileDB.getId());
            return ResponseEntity.status(HttpStatus.OK).body(map);

        } catch (Exception e) {

            Map<String, String> map = new HashMap<>();
            map.put("message", "Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(map);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FileDTO>> getAllFile(){

        List<FileDTO>files=fileDBService.getAllFiles().map(dbfile->{

            String fileDownloadUri= ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbfile.getId())
                    .toUriString();

            return  new FileDTO(dbfile.getName(), fileDownloadUri,dbfile.getType(), dbfile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id){
        FileDB fileDB=fileDBService.getFileById(id);

        //Asagidaki code dosya indirmeye yarayan kalip bir code.
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="+fileDB.getName()+"").body(fileDB.getData());

    }

    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayImage(@PathVariable String id){
        FileDB fileDB=fileDBService.getFileById(id);
        final HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(fileDB.getData(),headers,HttpStatus.CREATED);
    }

}
