package io.github.phongpcng_jpg.medical_supplies_management.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.phongpcng_jpg.medical_supplies_management.models.entities.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Optional<Supply> findByIdAndIsDeletedFalse(Long id);

    List<Supply> findAllByIsDeletedFalse();

    List<Supply> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);

}
