package com.newproject.travelbycaravan.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.newproject.travelbycaravan.model.enumeration.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="caravan_id", referencedColumnName = "id")
    private Caravan caravanId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User userId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Nederland")
    @NotNull(message = "Please enter pick up time")
    @Column(nullable = false)
    private LocalDateTime pickUpTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Nederland")
    @NotNull(message = "Please enter drop off time")
    @Column(nullable = false)
    private LocalDateTime dropOffTime;

    @Size(max = 150)
    @NotNull(message = "Please enter pick up location")
    @Column(length = 150, nullable = false)
    private String pickUpLocation;

    @Size(max = 150)
    @NotNull(message = "Please enter drop off location")
    @Column(length = 150, nullable = false)
    private String dropOffLocation;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private ReservationStatus status;

    @Column(nullable = false)
    private Double totalPrice;

    public Long getTotalHours(LocalDateTime pickUpTime,LocalDateTime dropOffTime){

        return ChronoUnit.HOURS.between(pickUpTime,dropOffTime);

    }


}
