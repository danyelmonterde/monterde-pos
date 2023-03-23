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
@Table(name = "sales")
public class Sales{

    @Id
    private int id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "total")
    private double total;

    @Column(name = "quantity")
    private int quantity;

}
