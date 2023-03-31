package com.monterdev.monterdepos.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "item_code")
    @Id
    private String itemCode;

    @Column(name = "in_stock")
    private int inStock;

    @Column(name = "low_stock")
    private int lowStock;

    @Column(name = "original_price")
    private double originalPrice;

    @Column(name = "average_cost")
    private double averageCost;

    @Column(name = "discountable")
    private boolean discountable;


}
