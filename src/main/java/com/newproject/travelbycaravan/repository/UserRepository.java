package com.newproject.travelbycaravan.repository;


import com.newproject.travelbycaravan.domain.User;
import com.newproject.travelbycaravan.exception.BadRequestException;
import com.newproject.travelbycaravan.exception.ResourceNotFoundException;
import com.newproject.travelbycaravan.projection.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {

    //@Query("Select u From User Where u.email=?1")
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email) throws ResourceNotFoundException;

    List<ProjectUser> findAllBy();

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.phoneNumber = ?4, u.email = ?5, u.address = ?6, " +
            "u.zipCode = ?7 WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String phoneNumber, String email, String address,
                String zipCode) throws BadRequestException;

}


