package com.newproject.travelbycaravan.service;

import com.newproject.travelbycaravan.domain.Caravan;
import com.newproject.travelbycaravan.domain.FileDB;
import com.newproject.travelbycaravan.dto.CaravanDTO;
import com.newproject.travelbycaravan.exception.BadRequestException;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import com.newproject.travelbycaravan.repository.CaravanRepository;
import com.newproject.travelbycaravan.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class CaravanService {

    private static final String IMAGE_NOT_FOUND_MSG = "image with id %s not found";
    private final CaravanRepository caravanRepository;
    private final FileDBRepository fileDBRepository;


    public List<CaravanDTO> fetchAllCaravans(){
        return caravanRepository.findAllCaravan();
    }


    public void add(Caravan caravan, String imageId) throws BadRequestException {
        FileDB fileDB=fileDBRepository.findById(imageId)
                .orElseThrow(()->new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));


        Set<FileDB> fileDBs= new HashSet<>();
        fileDBs.add(fileDB);

        caravan.setImage(fileDBs);
        caravanRepository.save(caravan);

    }
}
