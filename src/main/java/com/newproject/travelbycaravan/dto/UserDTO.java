package com.newproject.travelbycaravan.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newproject.travelbycaravan.domain.Role;
import com.newproject.travelbycaravan.domain.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Size(max = 15)
    @NotNull(message = "Please enter your first name")
    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    private String lastName;

   @JsonIgnore
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

    public void setRoles(Set<Role>roles){
        Set<String>roles1=new HashSet<>();
        Role[] role=roles.toArray(new Role[roles.size()]);

        for (int i = 0; i < roles.size(); i++) {
            if (role[i].getName().equals(UserRole.ROLE_ADMIN))
                roles1.add("administrator");
            else
                roles1.add("customer");

        }
        this.roles=roles1;
    }

    public UserDTO(String firstName, String lastName, String phoneNumber, String email, String address, String zipCode, Boolean builtIn, Set<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.builtIn = builtIn;
        this.roles = roles;
    }
}
