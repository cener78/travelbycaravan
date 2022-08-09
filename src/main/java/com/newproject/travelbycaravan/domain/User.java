package com.newproject.travelbycaravan.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Size(max = 15)
    @NotNull(message = "Please enter your first name")
    @Column(nullable = false,length = 15)
    private String firstName;

    @Size(max = 15)
    @NotNull(message = "Please enter your last name")
    @Column(nullable = false,length = 15)
    private String lastName;

    @Size(min=4, max = 60)
    @NotNull(message = "Please enter password")
    @Column(nullable = false,length = 120)
    private String password;

    @Pattern(regexp = "/(^\\+[0-9]{2}|^\\+[0-9]{2}\\(0\\)|^\\(\\+[0-9]{2}\\)\\(0\\)|^00[0-9]{2}|^0)([0-9]{9}$|[0-9\\-\\s]{10}$)/",
    message = "Please enter valid phone number")
    @Size(min=14, max=14)
    @NotNull(message = "Please enter your phone number")
    @Column(nullable = false, length = 14)
    private String phoneNumber;


    @Email(message = "Please enter valid email")
    @Size(min=5,max=120)
    @NotNull(message = "Please enter your email ")
    @Column(nullable = false, length = 14,unique = true)
    private  String email;



    @Column(nullable = false,length = 255)
    @NotNull(message = "Please enter your address")
    @Size(min=10, max=250)
    private  String address;

    @Column(nullable = false,length = 15)
    @NotNull(message = "Please enter your zipcode")
    @Size(max=15)
    private String zipCode;

    @Column(nullable = false)
    private Boolean builtIn; // if its value is true, that object will be prevented from being updated.
    // true olursa bazi kullanicilari update edilemez olarak ayarlanacak,
    // ornegin admin kullanicisinin silinmesini engelleyecek

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles=new HashSet<>();


}
