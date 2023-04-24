package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.view_models.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

}
