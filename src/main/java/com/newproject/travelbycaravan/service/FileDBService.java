package com.newproject.travelbycaravan.service;


import com.newproject.travelbycaravan.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileDBService {
    private final FileDBRepository fileDBRepository;

}
