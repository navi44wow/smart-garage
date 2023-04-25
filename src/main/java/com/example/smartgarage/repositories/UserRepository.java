package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.view_models.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    @Query("select u FROM User u")
    List<User> findAllUsers();

    Optional<User> findById(UUID id);

    @Query("SELECT u FROM User u JOIN FETCH u.vehicles v WHERE v.VIN like %:vin%")
    List<User> findByVehiclesVIN(Optional<String> vin);

    @Query("SELECT u FROM User u JOIN FETCH u.vehicles v WHERE v.carModelId.modelName like %:model%")
    List<User> findByVehiclesModel(Optional<String> model);

    @Query("SELECT u FROM User u JOIN FETCH u.vehicles v WHERE v.carModelId.brand.brandName like %:brand%")
    List<User> findByVehiclesBrand(Optional<String> brand);

    @Query("select u from User u where u.phoneNumber like :phoneNumber")
    Optional<User> findByPhoneNumber(String phoneNumber);
//    @Query("select u from User u where u.email like :email")

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.username like %:username%")
    List<User> findAllByUsername(Optional<String> username);

    @Query("select u from User u where u.email like %:email%")
    List<User> findAllByEmail(Optional<String> email);

    @Query("select u from User u where u.phoneNumber like %:phoneNumber%")
    List<User> findAllByPhoneNumber(Optional<String> phoneNumber);

    @Query("SELECT u FROM User u JOIN Vehicle v ON u.id = v.user.id JOIN Visit vs ON v.vehicleId = vs.vehicle.vehicleId GROUP BY u.id ORDER BY vs.startDate ASC")
    Set<User> sortByVisitDate();
    @Query("SELECT u FROM User u JOIN Vehicle v ON u.id = v.user.id JOIN Visit vs ON v.vehicleId = vs.vehicle.vehicleId GROUP BY u.id ORDER BY vs.startDate DESC")
    Set<User> sortByVisitDateDesc();

    @Query("SELECT DISTINCT u FROM User u JOIN u.vehicles v JOIN v.visits vs WHERE (:visitFirstDate IS NULL OR vs.startDate >= :visitFirstDate) AND (:visitLastDate IS NULL OR vs.startDate <= :visitLastDate)")
    Set<User> findByVisitsInRange(@Param("visitFirstDate") Optional<LocalDate> visitFirstDate, @Param("visitLastDate") Optional<LocalDate> visitLastDate);

}
