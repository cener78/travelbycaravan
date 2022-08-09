package com.newproject.travelbycaravan.repository;


import com.newproject.travelbycaravan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {

    //@Query("Select u From User Where u.email=?1")
    Optional<User> findByEmail(String email);

}
