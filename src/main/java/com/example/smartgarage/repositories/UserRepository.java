package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("select u FROM User u")
    List<User> findAllUsers();

    Optional<User> findById(Long id);

    @Query("select u from User u where u.phoneNumber like :phoneNumber")
    Optional<User> findByPhoneNumber(String phoneNumber);

//    @Query("select u from User u where u.email like :email")
    Optional<User> findByEmail(String email);

}
