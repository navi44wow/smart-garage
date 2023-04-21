package com.example.smartgarage.repositories;

import com.example.smartgarage.models.entities.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findByModelName(String modelName);

    List<Model> findAllByBrandId(Long id);

    List<Model> searchAllByBrand_BrandName(String brandName);
}
