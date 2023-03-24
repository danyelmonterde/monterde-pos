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
@Table(name = "sales_transaction")
public class SalesTransaction {

    @Id
    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "grand_total")
    private double grandTotal;

    @Column(name = "total_items")
    private int totalItems;

    @Column(name = "money_change")
    private double moneyChange;

    @Column(name = "discount")
    private double discount;

    @Column(name = "amount_paid")
    private double amountPaid;

    @Column(name = "date_transacted")
    private Date dateTransacted;

}
