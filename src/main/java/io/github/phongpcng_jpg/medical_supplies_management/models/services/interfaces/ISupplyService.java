package io.github.phongpcng_jpg.medical_supplies_management.models.services.interfaces;

import java.util.List;

import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.ExportSupplyDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.ImportSupplyDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.SupplyCreateDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.SupplyUpdateDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.DailyExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.SupplyResponseDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.TopExportDTO;

public interface ISupplyService {

    SupplyResponseDTO create(
            SupplyCreateDTO dto);

    SupplyResponseDTO update(
            Long id,
            SupplyUpdateDTO dto);

    void delete(Long id);

    List<SupplyResponseDTO> findAll();

    List<SupplyResponseDTO> search(
            String keyword);

    SupplyResponseDTO importSupply(
            Long id,
            ImportSupplyDTO dto);

    SupplyResponseDTO exportSupply(
            Long id,
            ExportSupplyDTO dto);

    List<DailyExportDTO> getDailyExportStatistics();

    TopExportDTO getTopExportStatistics();

}
