package io.github.phongpcng_jpg.medical_supplies_management.models.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.DailyExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.TopExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.entities.Transaction;
import io.github.phongpcng_jpg.medical_supplies_management.models.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT new io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.DailyExportDTO(
            s.id,
            s.name,
            SUM(t.amount)
        )
        FROM Transaction t
        JOIN t.supply s
        WHERE
            t.type = :type
            AND t.createdAt BETWEEN :start AND :end
        GROUP BY s.id, s.name
        ORDER BY SUM(t.amount) DESC
    """)
    List<DailyExportDTO> getDailyExportStatistics(
            @Param("type") TransactionType type,
            @Param("start") Instant startOfDay,
            @Param("end") Instant endOfDay
    );



    @Query("""
        SELECT new io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.TopExportDTO(
            s.id,
            s.name,
            SUM(t.amount)
        )
        FROM Transaction t
        JOIN t.supply s
        WHERE t.type = :type
        GROUP BY s.id, s.name
        ORDER BY SUM(t.amount) DESC
    """)
    List<TopExportDTO> getTopExportStatistics(
            @Param("type") TransactionType type,
            Pageable pageable
    );

}