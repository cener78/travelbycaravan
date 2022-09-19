package com.newproject.travelbycaravan.dto;


import com.newproject.travelbycaravan.model.Caravan;
import com.newproject.travelbycaravan.model.FileDB;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CaravanDTO {

    private Long id;

    private String model;

    private Integer beds;

    private Integer seats;

    private Integer luggage;

    private String transmission;

    private Boolean airConditioning;

    private Integer age;

    private Double pricePerDay;

    private String fuelType;

    private Boolean builtIn;

    private Set<String> image;

    public CaravanDTO(Caravan caravan) {
        this.id = caravan.getId();
        this.model = caravan.getModel();
        this.beds = caravan.getBeds();
        this.seats = caravan.getSeats();
        this.luggage = caravan.getLuggage();
        this.transmission = caravan.getTransmission();
        this.airConditioning = caravan.getAirConditioning();
        this.age = caravan.getAge();
        this.pricePerDay = caravan.getPricePerDay();
        this.fuelType = caravan.getFuelType();
        this.builtIn = caravan.getBuiltIn();
        this.image = getImageId(caravan.getImage());
    }

    public Set<String> getImageId(Set<FileDB>images){
        Set<String>img=new HashSet<>();
        FileDB[]fileDB=images.toArray(new FileDB[images.size()]);

        for (int i = 0; i < images.size(); i++) {

            img.add(fileDB[i].getId());
        }
        return  img;

    }

}
