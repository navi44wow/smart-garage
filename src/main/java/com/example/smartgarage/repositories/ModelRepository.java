package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.CarModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<CarModel, Long> {

    Optional<CarModel> findByModelName(String modelName);

    List<CarModel> findAllByBrandId(Long id);

    List<CarModel> searchAllByBrand_BrandName(String brandName);
}
