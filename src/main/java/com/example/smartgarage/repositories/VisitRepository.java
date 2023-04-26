package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select u FROM User u")
    List<User> findAllUsers();

    @Query("select v from Visit v where v.vehicle.user.username like %:username%")
    List<Visit> findAllByUsername(Optional<String> username);

    @Query("select v from Visit v where v.status.name like %:status%")
    List<Visit> findAllByStatus(Optional<String> status);


//    @Query("select v from Visit v where v.startDate like %:startDate%")
//    List<Visit> findAllByFirstDate(@Param("startDate") String startDate);
}
