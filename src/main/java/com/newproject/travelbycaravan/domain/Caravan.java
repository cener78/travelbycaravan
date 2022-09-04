package com.newproject.travelbycaravan.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="caravans")
public class Caravan   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Size(max = 30,message = "Size is exceeded")
    @NotNull(message = "please enter the caravan model")
    @Column(length = 30,nullable = false)
    private String model;

    @NotNull(message = "Please enter the caravan beds")
    @Column(nullable = false)
    private Integer beds;

    @NotNull(message = "Please enter the caravan seats")
    @Column(nullable = false)
    private Integer seats;

    @NotNull(message = "Please enter the caravan luggage")
    @Column(nullable = false)
    private String luggage;

    @Size(max=30)
    @NotNull(message = "Please enter the caravan transmission")
    @Column(length = 30, nullable = false)
    private String transmission;

    @NotNull(message = "Please enter the caravan air conditioning")
    @Column(nullable = false)
    private Boolean airConditioning;

    @NotNull(message = "Please enter the caravan age")
    @Column(nullable = false)
    private Integer age;


    @NotNull(message = "Please enter the caravan price")
    @Column(nullable = false)
    private Double pricePerDay;

    @Size(max=30)
    @NotNull(message = "Please enter the caravan fuel type")
    @Column(length = 30, nullable = false)
    private String fuelType;

    private Boolean builtIn;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="caravan_image",
            joinColumns = @JoinColumn(name="car_id")
            ,inverseJoinColumns = @JoinColumn(name="file_id"))

    private Set<FileDB> image;


}
