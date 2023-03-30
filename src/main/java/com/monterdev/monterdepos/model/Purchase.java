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
@Table(name = "purchase")
public class Purchase {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "cost")
    private double cost;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total")
    private double total;

    @Column(name = "date_bought_from_supplier")
    private Date dateBoughtFromSupplier;

}
