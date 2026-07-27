package io.github.phongpcng_jpg.medical_supplies_management.models.services.impls;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.phongpcng_jpg.medical_supplies_management.exceptions.ApiException;
import io.github.phongpcng_jpg.medical_supplies_management.exceptions.ErrorCode;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.ExportSupplyDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.ImportSupplyDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.SupplyCreateDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.requests.SupplyUpdateDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.DailyExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.ImageUploadResult;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.SupplyResponseDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.dtos.responses.TopExportDTO;
import io.github.phongpcng_jpg.medical_supplies_management.models.entities.Supply;
import io.github.phongpcng_jpg.medical_supplies_management.models.entities.Transaction;
import io.github.phongpcng_jpg.medical_supplies_management.models.enums.TransactionType;
import io.github.phongpcng_jpg.medical_supplies_management.models.repositories.SupplyRepository;
import io.github.phongpcng_jpg.medical_supplies_management.models.repositories.TransactionRepository;
import io.github.phongpcng_jpg.medical_supplies_management.models.services.interfaces.ICloudinaryService;
import io.github.phongpcng_jpg.medical_supplies_management.models.services.interfaces.ISupplyService;
import io.github.phongpcng_jpg.medical_supplies_management.utils.mappers.SupplyMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplyServiceImpl implements ISupplyService{

    private final SupplyRepository supplyRepository;

    private final TransactionRepository transactionRepository;

    private final ICloudinaryService cloudinaryService;

    private static final Logger historyLogger =
            LoggerFactory.getLogger("history");

    private Supply getSupplyById(Long id) {

        return supplyRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ApiException(
                                ErrorCode.SUPPLY_NOT_FOUND
                        )
                );

    }

    private void updateSupplyInformation(
            Supply supply,
            SupplyUpdateDTO dto) {

        if (dto.getName() != null) {
            supply.setName(dto.getName());
        }

        if (dto.getSpecification() != null) {
            supply.setSpecification(dto.getSpecification());
        }

        if (dto.getProvider() != null) {
            supply.setProvider(dto.getProvider());
        }

        if (dto.getUnit() != null) {
            supply.setUnit(dto.getUnit());
        }

    }

    private void updateSupplyImage(
            Supply supply,
            MultipartFile image) {

        if (image == null || image.isEmpty()) {
            return;
        }

        cloudinaryService.delete(
                supply.getPublicId()
        );

        ImageUploadResult uploadResult =
                cloudinaryService.upload(image);

        supply.setImageUrl(
                uploadResult.getImageUrl()
        );

        supply.setPublicId(
                uploadResult.getPublicId()
        );

    }

    private void createTransaction(
            Supply supply,
            TransactionType type,
            Integer amount) {

        Transaction transaction = Transaction.builder()
                .type(type)
                .amount(amount)
                .supply(supply)
                .build();

        transactionRepository.save(transaction);

    }

    private void updateQuantity(
            Supply supply,
            TransactionType type,
            Integer amount) {

        if (type == TransactionType.IMPORT) {

            supply.setQuantity(
                    supply.getQuantity() + amount
            );

            return;
        }

        if (supply.getQuantity() < amount) {

            log.error(
                    "Export failed. Supply id={}, requested={}, available={}",
                    supply.getId(),
                    amount,
                    supply.getQuantity()
            );

            throw new ApiException(
                    ErrorCode.INSUFFICIENT_QUANTITY
            );

        }

        supply.setQuantity(
                supply.getQuantity() - amount
        );

    }

    @Override
    @Transactional
    public SupplyResponseDTO create(SupplyCreateDTO dto) {

        ImageUploadResult uploadResult =
                cloudinaryService.upload(dto.getImage());

        Supply supply = Supply.builder()
                .name(dto.getName())
                .specification(dto.getSpecification())
                .provider(dto.getProvider())
                .unit(dto.getUnit())
                .quantity(0)
                .imageUrl(
                        uploadResult != null
                                ? uploadResult.getImageUrl()
                                : null
                )
                .publicId(
                        uploadResult != null
                                ? uploadResult.getPublicId()
                                : null
                )
                .build();

        supply = supplyRepository.save(supply);

        log.info(
                "Created supply '{}' with id {}",
                supply.getName(),
                supply.getId()
        );

        return SupplyMapper.toResponseDTO(supply);

    }

    @Override
    @Transactional
    public SupplyResponseDTO update(
            Long id,
            SupplyUpdateDTO dto) {

        Supply supply = getSupplyById(id);

        updateSupplyInformation(
                supply,
                dto
        );

        updateSupplyImage(
                supply,
                dto.getImage()
        );

        supply = supplyRepository.save(supply);

        log.info(
                "Updated supply '{}'",
                supply.getName()
        );

        return SupplyMapper.toResponseDTO(supply);

    }

    @Override
    @Transactional
    public void delete(Long id) {

        Supply supply = getSupplyById(id);

        supply.setIsDeleted(true);

        supplyRepository.save(supply);

        log.info(
                "Deleted supply '{}'",
                supply.getName()
        );

    }

    @Override
    @Transactional
    public List<SupplyResponseDTO> findAll() {

        return SupplyMapper.toResponseDTOList(
                supplyRepository.findAllByIsDeletedFalse()
        );

    }

    @Override
    @Transactional
    public List<SupplyResponseDTO> search(
            String keyword) {

        List<Supply> supplies =
                supplyRepository
                        .findByNameContainingIgnoreCaseAndIsDeletedFalse(
                                keyword
                        );

        if (supplies.isEmpty()) {

            log.info(
                    "No supplies found with keyword '{}'",
                    keyword
            );

        }

        return SupplyMapper.toResponseDTOList(supplies);

    }

    @Override
    @Transactional
    public SupplyResponseDTO importSupply(
            Long id,
            ImportSupplyDTO dto) {

        Supply supply = getSupplyById(id);

        Integer oldQuantity = supply.getQuantity();

        updateQuantity(
                supply,
                TransactionType.IMPORT,
                dto.getAmount()
        );

        createTransaction(
                supply,
                TransactionType.IMPORT,
                dto.getAmount()
        );

        supply = supplyRepository.save(supply);

        log.info(
                "Import supply id={}, +{}, old={}, new={}",
                supply.getId(),
                dto.getAmount(),
                oldQuantity,
                supply.getQuantity()
        );

        historyLogger.info(
                "IMPORT | Supply={} | Amount={} | NewQuantity={}",
                supply.getName(),
                dto.getAmount(),
                supply.getQuantity()
        );

        return SupplyMapper.toResponseDTO(supply);

    }

    @Override
    @Transactional
    public SupplyResponseDTO exportSupply(
            Long id,
            ExportSupplyDTO dto) {

        Supply supply = getSupplyById(id);

        updateQuantity(
                supply,
                TransactionType.EXPORT,
                dto.getAmount()
        );

        createTransaction(
                supply,
                TransactionType.EXPORT,
                dto.getAmount()
        );

        supply = supplyRepository.save(supply);

        log.info(
                "Export supply id={}, -{}, remain={}",
                supply.getId(),
                dto.getAmount(),
                supply.getQuantity()
        );

        historyLogger.info(
                "EXPORT | Supply={} | Amount={} | Remaining={}",
                supply.getName(),
                dto.getAmount(),
                supply.getQuantity()
        );

        return SupplyMapper.toResponseDTO(supply);

    }

    @Override
    @Transactional
    public List<DailyExportDTO> getDailyExportStatistics() {

        ZoneId zoneId = ZoneId.systemDefault();

        Instant start = LocalDate.now()
                .atStartOfDay(zoneId)
                .toInstant();

        Instant end = LocalDate.now()
                .plusDays(1)
                .atStartOfDay(zoneId)
                .minusNanos(1)
                .toInstant();

        log.info("Daily export statistic started.");

        List<DailyExportDTO> result =
                transactionRepository.getDailyExportStatistics(
                        TransactionType.EXPORT,
                        start,
                        end
                );

        log.info(
                "Daily export statistic completed. {} record(s).",
                result.size()
        );

        return result;

    }

    @Override
    @Transactional
    public TopExportDTO getTopExportStatistics() {

        List<TopExportDTO> result =
                transactionRepository.getTopExportStatistics(
                        TransactionType.EXPORT,
                        PageRequest.of(0, 1)
                );

        if (result.isEmpty()) {

            throw new ApiException(
                    ErrorCode.NO_TRANSACTION_DATA,
                    "No export transaction found."
            );

        }

        return result.get(0);

    }

}
