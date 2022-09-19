package com.newproject.travelbycaravan.service;

import com.newproject.travelbycaravan.model.Caravan;
import com.newproject.travelbycaravan.model.FileDB;
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
    private final  static String CARAVAN_NOT_FOUND_MSG="caravan with id %d not found";


    public List<CaravanDTO> fetchAllCaravans(){
        return caravanRepository.findAllCaravan();
    }


    public void add(Caravan caravan, String imageId) throws BadRequestException {
        FileDB fileDB = fileDBRepository.findById(imageId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));

        Set<FileDB> fileDBs = new HashSet<>();
        fileDBs.add(fileDB);

        caravan.setImage(fileDBs);
        caravan.setBuiltIn(false);
        caravanRepository.save(caravan);
    }

    public CaravanDTO findById(Long id) throws ResourceNotFoundException {
      return caravanRepository.findByIdOrderById(id)
              .orElseThrow(()->new ResourceNotFoundException(String.format(CARAVAN_NOT_FOUND_MSG,id)));

    }

    public void updateCaravan(Long id, String imageId, Caravan caravan) throws BadRequestException {
        caravan.setId(id);
        FileDB fileDB = fileDBRepository.findById(imageId).get();

        Caravan caravan1 = caravanRepository.getById(id);

        if (caravan1.getBuiltIn())
            throw new BadRequestException("You dont have permission to update car!");

        caravan.setBuiltIn(false);

        Set<FileDB> fileDBs = new HashSet<>();
        fileDBs.add(fileDB);

        caravan.setImage(fileDBs);

        caravanRepository.save(caravan);
    }

    public void removeCaravanById(Long id) throws BadRequestException{

        Caravan caravan=caravanRepository.findById(id)
                .orElseThrow(()-> new BadRequestException(String.format(CARAVAN_NOT_FOUND_MSG,id)));

        if(caravan.getBuiltIn())
            throw new BadRequestException("You do not have permission to delete the caravan");

        caravanRepository.deleteById(id);
    }
}
