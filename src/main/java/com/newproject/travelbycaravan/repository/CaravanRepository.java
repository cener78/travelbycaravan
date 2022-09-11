package com.newproject.travelbycaravan.repository;

import com.newproject.travelbycaravan.domain.Caravan;
import com.newproject.travelbycaravan.dto.CaravanDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaravanRepository extends JpaRepository<Caravan,Long> {

    @Query("SELECT new com.newproject.travelbycaravan.dto.CaravanDTO(c) from Caravan c")
    public List<CaravanDTO> findAllCaravan();
}
