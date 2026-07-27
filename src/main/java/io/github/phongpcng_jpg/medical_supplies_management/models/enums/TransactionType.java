package io.github.phongpcng_jpg.medical_supplies_management.models.enums;

import io.github.phongpcng_jpg.medical_supplies_management.models.entities.Transaction;

/**
 * Defines the types of inventory transactions that affect a medical supply.
 *
 * <p>
 * Each transaction represents an inventory movement recorded in the system.
 * The transaction type determines whether the inventory quantity increases
 * or decreases.
 * </p>
 *
 * <p>
 * Business rules:
 * </p>
 * <ul>
 *     <li>{@link #IMPORT} increases the available inventory.</li>
 *     <li>{@link #EXPORT} decreases the available inventory.</li>
 *     <li>Inventory updates should always create a corresponding transaction
 *     record for audit purposes.</li>
 * </ul>
 *
 * @author Nguyen Que Phong
 * @version 1.0.0
 * @since 1.0.0
 *
 * @see Transaction
 */
public enum TransactionType {

    /**
     * Represents an inventory import transaction.
     *
     * <p>
     * Import transactions increase the available quantity of a supply,
     * typically due to purchasing, restocking, or returned inventory.
     * </p>
     */
    IMPORT,

    /**
     * Represents an inventory export transaction.
     *
     * <p>
     * Export transactions decrease the available quantity of a supply,
     * typically because the supply is consumed, transferred,
     * or discarded.
     * </p>
     */
    EXPORT
}
