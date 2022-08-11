package com.newproject.travelbycaravan.service;

import com.newproject.travelbycaravan.domain.Role;
import com.newproject.travelbycaravan.domain.User;
import com.newproject.travelbycaravan.domain.enumeration.UserRole;
import com.newproject.travelbycaravan.exception.BadRequestException;
import com.newproject.travelbycaravan.exception.ConflictException;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import com.newproject.travelbycaravan.repository.RoleRepository;
import com.newproject.travelbycaravan.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) throws BadRequestException {
        if(userRepository.existsByEmail(user.getEmail())){
             throw new ConflictException("Error: Email is already in use!");
        }
        
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        Set<Role> roles =new HashSet<>();
        Role customerRole=roleRepository.findByName(UserRole.ROLE_CUSTOMER)  // name is not user's name, name is role's name
                .orElseThrow(()->new ResourceNotFoundException("Error: Role is not found."));
        roles.add(customerRole);
        user.setRoles(roles);
        userRepository.save(user);

     }





}
