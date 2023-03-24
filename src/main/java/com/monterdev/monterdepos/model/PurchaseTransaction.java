package com.monterdev.monterdepos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_transaction")
public class PurchaseTransaction {

    @Id
    private String transactionNumber;

    @Column(name = "grand_total")
    private double grandTotal;

    @Column(name = "total_items")
    private int totalItems;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "supplier_location")
    private String supplierLocation;

    @Column(name = "date_transacted")
    private Date dateTransacted;

    @Column(name = "date_bought_from_supplier")
    private Date dateBoughtFromSupplier;
}
