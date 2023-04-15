package com.monterdev.monterdepos.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = "item_no")
    private int sku;

    @CsvBindByName(column = "bar_code")
    private String barCode;

    @CsvBindByName(column = "item_code")
    private String itemCode;

    @CsvBindByName(column = "qty")
    private int quantity;

    @CsvBindByName(column = "item_name")
    private String itemName;

    @CsvBindByName(column = "total_price")
    private double totalPrice;

    @CsvBindByName(column = "unit_price")
    private double unitPrice;

    @CsvBindByName(column = "ten_percent")
    private double tenPercent;

    @CsvBindByName(column = "selling_price")
    private double sellingPrice;

    @CsvBindByName(column = "date_of_expiration")
    private String dateOfExpiration;


}
