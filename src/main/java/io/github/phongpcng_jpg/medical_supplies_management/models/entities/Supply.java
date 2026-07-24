package io.github.phongpcng_jpg.medical_supplies_management.models.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a medical supply or consumable item managed by the hospital.
 *
 * <p>
 * A supply is an inventory item used during healthcare services such as
 * examinations, treatments, surgeries, and laboratory procedures.
 * Each supply maintains its current inventory quantity together with
 * a complete history of inventory transactions.
 * </p>
 *
 * <p>
 * Business rules:
 * </p>
 * <ul>
 *     <li>Each supply must have a name, specification and supplier.</li>
 *     <li>The inventory quantity cannot be negative.</li>
 *     <li>Inventory changes must be recorded through {@link Transaction}
 *     entities.</li>
 *     <li>Supplies are logically deleted using the {@code isDeleted} flag.</li>
 * </ul>
 *
 * <p>
 * Database mapping:
 * </p>
 * <ul>
 *     <li>Table: {@code supplies}</li>
 *     <li>Primary Key: {@code id}</li>
 *     <li>One-to-Many relationship with {@link Transaction}</li>
 *     <li>Audit columns:
 *         {@code created_at},
 *         {@code updated_at}
 *     </li>
 * </ul>
 *
 * @author Nguyen Que Phong
 * @version 1.0.0
 * @since 1.0.0
 *
 * @see Transaction
 */
@Entity
@Table(name = "supplies")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supply {

    /** Unique identifier of the supply. */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    /**
     * Display name of the medical supply.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Technical specification describing the supply.
     *
     * <p>
     * Examples include dimensions, material,
     * concentration, packaging or model.
     * </p>
     */
    @Column(nullable = false)
    private String specification;

    /**
     * Supplier or manufacturer providing this supply.
     */
    @Column(nullable = false)
    private String provider;

    /**
     * Unit price of the supply.
     *
     * <p>
     * Stored as {@link BigDecimal} to avoid floating-point precision issues.
     * </p>
     */
    @Column(nullable = false)
    private BigDecimal unit;

    /**
     * Current quantity available in inventory.
     *
     * <p>
     * This value is automatically updated whenever inventory
     * transactions are processed.
     * </p>
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    /**
     * Public URL of the supply image stored in Cloudinary.
     *
     * <p>
     * May be {@code null} if no image has been uploaded.
     * </p>
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Cloudinary public identifier of the uploaded image.
     *
     * <p>
     * Used when replacing or deleting the image.
     * </p>
     */
    @Column(name = "public_id")
    private String publicId;

    /**
     * Indicates whether the supply has been logically deleted.
     *
     * <p>
     * Soft-deleted supplies remain in the database for historical
     * and auditing purposes.
     * </p>
     */
    @Column(
        name = "is_deleted", 
        nullable = false
    )
    @Builder.Default
    private Boolean isDeleted = false;

    /**
     * Timestamp when this supply was first created.
     */
    @CreationTimestamp
    @Column(
        name = "created_at", 
        nullable = false, 
        updatable = false
    )
    private Instant createdAt;

    /**
     * Timestamp of the latest modification.
     */
    @UpdateTimestamp
    @Column(
        name = "updated_at", 
        nullable = false
    )
    private Instant updatedAt;

    /**
     * Inventory transaction history of this supply.
     *
     * <p>
     * A supply may have zero or more transactions.
     * Each transaction records a single inventory movement.
     * </p>
     */
    @OneToMany(mappedBy = "supply")
    @ToString.Exclude
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

}
