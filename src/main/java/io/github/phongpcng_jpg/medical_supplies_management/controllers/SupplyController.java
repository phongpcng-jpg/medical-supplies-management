package io.github.phongpcng_jpg.medical_supplies_management.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.phongpcng_jpg.medical_supplies_management.exceptions.ErrorCode;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.ExportSupplyDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.ImportSupplyDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.SupplyCreateDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.SupplyUpdateDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.DailyExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.SupplyResponseDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.TopExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.wrappers.ApiResponse;
import io.github.phongpcng_jpg.medical_supplies_management.models.services.interfaces.ISupplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/supplies")
@RequiredArgsConstructor
@Validated
public class SupplyController {

    private final ISupplyService supplyService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SupplyResponseDTO>> create(
            @Valid
            @ModelAttribute
            SupplyCreateDTO dto) {

        SupplyResponseDTO response = supplyService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                ErrorCode.CREATED,
                                response
                        )
                );

    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<SupplyResponseDTO>> update(
            @PathVariable Long id,
            @Valid
            @ModelAttribute
            SupplyUpdateDTO dto) {

        SupplyResponseDTO response =
                supplyService.update(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        response
                )
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        supplyService.delete(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplyResponseDTO>>> findAll() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        supplyService.findAll()
                )
        );

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SupplyResponseDTO>>> search(
                @RequestParam String name) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        supplyService.search(name)
                )
        );

    }

    @PatchMapping("/{id}/import")
    public ResponseEntity<ApiResponse<SupplyResponseDTO>> importSupply(
                @PathVariable Long id,
                @Valid
                @RequestBody
                ImportSupplyDTO dto) {

        SupplyResponseDTO response =
                supplyService.importSupply(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        response
                )
        );

    }

    @PatchMapping("/{id}/export")
    public ResponseEntity<ApiResponse<SupplyResponseDTO>> exportSupply(
                @PathVariable Long id,
                @Valid
                @RequestBody
                ExportSupplyDTO dto) {

        SupplyResponseDTO response =
                supplyService.exportSupply(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        response
                )
        );

    }

    @GetMapping("/statistics/daily-export")
    public ResponseEntity<ApiResponse<List<DailyExportDTO>>> getDailyExportStatistics() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        supplyService.getDailyExportStatistics()
                )
        );

    }

    @GetMapping("/statistics/top-export")
    public ResponseEntity<ApiResponse<TopExportDTO>> getTopExportStatistics() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ErrorCode.SUCCESS,
                        supplyService.getTopExportStatistics()
                )
        );

    }

}