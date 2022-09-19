package com.newproject.travelbycaravan.repository;

import com.newproject.travelbycaravan.model.Caravan;
import com.newproject.travelbycaravan.dto.CaravanDTO;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CaravanRepository extends JpaRepository<Caravan,Long> {

    @Query("SELECT new com.newproject.travelbycaravan.dto.CaravanDTO(c) from Caravan c")
    public List<CaravanDTO> findAllCaravan();

   // @Query("SELECT new com.newproject.travelbycaravan.dto.CaravanDTO(c) from Caravan caravan where caravan.id=?1")
    Optional<CaravanDTO> findByIdOrderById(Long id) throws ResourceNotFoundException;
}


