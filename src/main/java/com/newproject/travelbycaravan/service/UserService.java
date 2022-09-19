package com.newproject.travelbycaravan.service;

import com.newproject.travelbycaravan.model.Role;
import com.newproject.travelbycaravan.model.User;
import com.newproject.travelbycaravan.model.enumeration.UserRole;
import com.newproject.travelbycaravan.dto.AdminDTO;
import com.newproject.travelbycaravan.dto.UserDTO;
import com.newproject.travelbycaravan.exception.AuthException;
import com.newproject.travelbycaravan.exception.BadRequestException;
import com.newproject.travelbycaravan.exception.ConflictException;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import com.newproject.travelbycaravan.projection.ProjectUser;
import com.newproject.travelbycaravan.repository.RoleRepository;
import com.newproject.travelbycaravan.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";

    public List<ProjectUser> fetchAllUser() {
        return userRepository.findAllBy();
    }

    // DTO data transfer object
    public UserDTO findById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        UserDTO userDTO = new UserDTO();
        userDTO.setRoles(user.getRoles());
        return new UserDTO(user.getFirstName(), user.getLastName(),
                user.getPhoneNumber(), user.getEmail(), user.getAddress()
                , user.getZipCode(), user.getBuiltIn(), userDTO.getRoles());
    }


    public void register(User user) throws BadRequestException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Error: Email is already in use!");
        }


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        Role customerRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)  // name is not user's name, name is role's name
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        roles.add(customerRole);
        user.setRoles(roles);
        userRepository.save(user);

    }

    public void login(String email, String password) throws AuthException {

        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (!BCrypt.checkpw(password, user.get().getPassword())) {
                throw new AuthException("Invalid credentials");
            }
        } catch (Exception e) {
            throw new AuthException("Invalid credentials");
        }
    }

    public void updateUser(Long id, UserDTO userDao) throws BadRequestException {

        boolean emailExists = userRepository.existsByEmail(userDao.getEmail());
        Optional<User> userDetails = userRepository.findById(id);

        if (userDetails.get().getBuiltIn()) {
            throw new ResourceNotFoundException("You dont have permission to update user info!");
        }

        if (emailExists && !userDao.getEmail().equals(userDetails.get().getEmail())) {
            throw new ConflictException("Error: Email is already in use!");
        }

        userRepository.update(id, userDao.getFirstName(), userDao.getLastName(), userDao.getPhoneNumber(),
                userDao.getEmail(), userDao.getAddress(), userDao.getZipCode());
    }


    public void updatePassword(Long id, String newPassword, String oldPassword) throws BadRequestException {
        Optional<User> user = userRepository.findById(id);
        if (user.get().getBuiltIn()) {
            throw new BadRequestException("You donot have permission to update password");
        }
        if (!BCrypt.hashpw(oldPassword, user.get().getPassword()).equals(user.get().getPassword()))
            throw new BadRequestException("Password doesnot match");
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.get().setPassword(hashedPassword);
        userRepository.save(user.get());
    }

    public void updateUserAuth(Long id, AdminDTO adminDTO) throws BadRequestException {
        boolean emailExist = userRepository.existsByEmail(adminDTO.getEmail());

        Optional<User> userDetails = userRepository.findById(id);
        if (userDetails.get().getBuiltIn()) {
            throw new BadRequestException("You do not have permission to update user info");
        }
        adminDTO.setBuiltIn(false);
        if (emailExist && !adminDTO.getEmail().equals(userDetails.get().getEmail())) {// email databasede varsa ve yeni girilen ile eskisi esitdegilse
                                                                                        //yeni girilenle eski girilen esitse sonun yok cunku kullanici degistirmek istememis demektir.
            throw new ConflictException("Error: Email is already in use");

        }

        if (adminDTO.getPassword() == null)
            adminDTO.setPassword(userDetails.get().getPassword());

        else {
            String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());
            adminDTO.setPassword(encodedPassword);
        }

        Set<String> userRoles = adminDTO.getRoles();
        Set<Role> roles = addRoles(userRoles);

        User user = new User(id,adminDTO.getFirstName(),adminDTO.getLastName()
                ,adminDTO.getPassword(),adminDTO.getPhoneNumber(),adminDTO.getEmail()
                ,adminDTO.getAddress(),adminDTO.getZipCode(),adminDTO.getBuiltIn(),roles);

        userRepository.save(user);


    }

    public Set<Role> addRoles(Set<String> userRoles) {
        Set<Role> roles = new HashSet<>();
        if (userRoles == null) {
            Role userRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error:Role is not found"));
            roles.add(userRole);
        } else {

            userRoles.forEach(role -> {
                switch (role) {
                    case "administrator":
                        Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);

                        break;
                    case "CustomerService":
                        Role customerServiceRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER_SERVICE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(customerServiceRole);
                        break;
                    case "Manager":
                        Role managerRole = roleRepository.findByName(UserRole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(managerRole);
                        break;

                    default:
                        Role customerRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(customerRole);
                        break;

                }


            });
        }
        return roles;
    }

    public void removeById(Long id) throws ResourceNotFoundException {
        User user=new User();
        userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG,id)));
        if(user.getBuiltIn())
            throw new BadRequestException("You do not have permission to delete user");

        userRepository.deleteById(id);

    }
}
