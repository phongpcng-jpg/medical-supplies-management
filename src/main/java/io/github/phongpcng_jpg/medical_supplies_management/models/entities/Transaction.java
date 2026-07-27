package io.github.phongpcng_jpg.medical_supplies_management.models.entities;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import io.github.phongpcng_jpg.medical_supplies_management.models.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an inventory transaction performed on a medical supply.
 *
 * <p>
 * Every inventory movement is recorded as a transaction to provide
 * a complete audit trail. Transactions are immutable historical
 * records once created.
 * </p>
 *
 * <p>
 * Business rules:
 * </p>
 * <ul>
 *     <li>Each transaction belongs to exactly one supply.</li>
 *     <li>A transaction is either an {@link TransactionType#IMPORT}
 *     or an {@link TransactionType#EXPORT}.</li>
 *     <li>The transaction amount must be greater than zero.</li>
 *     <li>Creating a transaction updates the current inventory quantity
 *     of the associated supply.</li>
 * </ul>
 *
 * <p>
 * Database mapping:
 * </p>
 * <ul>
 *     <li>Table: {@code transactions}</li>
 *     <li>Primary Key: {@code id}</li>
 *     <li>Many-to-One relationship with {@link Supply}</li>
 * </ul>
 *
 * @author Nguyen Que Phong
 * @version 1.0.0
 * @since 1.0.0
 *
 * @see Supply
 * @see TransactionType
 */
@Entity
@Table(name = "transactions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    /**
     * Unique identifier of the transaction.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    /**
     * Type of inventory movement.
     *
     * <p>
     * Determines whether the inventory quantity is increased
     * or decreased.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    /**
     * Quantity affected by the transaction.
     *
     * <p>
     * Represents the number of supply units imported or exported.
     * This value should always be greater than zero.
     * </p>
     */
    @Column(nullable = false)
    private Integer amount;

    /**
     * Timestamp when the transaction was recorded.
     *
     * <p>
     * Automatically generated when the entity is persisted.
     * </p>
     */
    @CreationTimestamp
    @Column(
        name = "created_at", 
        nullable = false, 
        updatable = false
    )
    private Instant createdAt;

    /**
     * Supply associated with this inventory transaction.
     *
     * <p>
     * Every transaction must belong to exactly one supply,
     * while a supply may have many transactions.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "supply_id",
        nullable = false
    )
    @ToString.Exclude
    private Supply supply;

}
