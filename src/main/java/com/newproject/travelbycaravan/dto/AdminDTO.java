package com.newproject.travelbycaravan.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {


    @Size(max = 15)
    @NotNull(message = "Please enter your first name")
    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    private String lastName;

    @Size(min=4, max = 60)
    private String password;

    @Pattern(regexp = "^((\\+|00(\\s|\\s?\\-\\s?)?)31(\\s|\\s?\\-\\s?)?(\\(0\\)[\\-\\s]?)?|0)[1-9]((\\s|\\s?\\-\\s?)?[0-9])((\\s|\\s?-\\s?)?[0-9])((\\s|\\s?-\\s?)?[0-9])\\s?[0-9]\\s?[0-9]\\s?[0-9]\\s?[0-9]\\s?[0-9]$",
            message = "Please enter valid phone number")
    @Size(min=10, max=16)
    @NotNull(message = "Please enter your phone number")
    private String phoneNumber;


    @Email(message = "Please enter valid email")
    @Size(min=5,max=120)
    @NotNull(message = "Please enter your email ")
    private  String email;

    @NotNull(message = "Please enter your address")
    @Size(min=10, max=250)
    private  String address;

    @NotNull(message = "Please enter your zipcode")
    @Size(max=15)
    private String zipCode;

    private Boolean builtIn;

    private Set<String> roles;
}
