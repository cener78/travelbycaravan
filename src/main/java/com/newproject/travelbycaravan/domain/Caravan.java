package com.newproject.travelbycaravan.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "caravans")
public class Caravan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30, message = "Size is exceeded")
    @NotNull(message = "Please enter the car model")
    @Column(length = 30, nullable = false)
    private String model;

    @NotNull(message = "Please enter the car doors")
    @Column(nullable = false)
    private Integer beds;

    @NotNull(message = "Please enter the car seats")
    @Column(nullable = false)
    private Integer seats;

    @NotNull(message = "Please enter the car luggage")
    @Column(nullable = false)
    private Integer luggage;

    @Size(max = 30, message = "Size is exceeded")
    @NotNull(message = "Please enter the car transmission")
    @Column(length = 30, nullable = false)
    private String transmission;

    @NotNull(message = "Please enter the car air conditioning")
    @Column(nullable = false)
    private Boolean airConditioning;

    @NotNull(message = "Please enter the car age")
    @Column(nullable = false)
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "caravan_image",
            joinColumns = @JoinColumn(name = "caravan_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileDB> image;

    @NotNull(message = "Please enter price per hour of the car")
    @Column(nullable = false)
    private Double pricePerDay;

    @Size(max = 30, message = "Size is exceeded")
    @NotNull(message = "Please enter the car fuel type")
    @Column(nullable = false, length = 30)
    private String fuelType;

    private Boolean builtIn;

    public Caravan(String model, Integer doors, Integer seats, Integer luggage, String transmission,
               Boolean airConditioning, Integer age, Set<FileDB> image, Double pricePerDay, String fuelType) {
        this.model = model;
        this.beds = doors;
        this.seats = seats;
        this.luggage = luggage;
        this.transmission = transmission;
        this.airConditioning = airConditioning;
        this.age = age;
        this.image = image;
        this.pricePerDay = pricePerDay;
        this.fuelType = fuelType;
    }
}