package com.monterdev.monterdepos.model;


import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    @Id
    private String itemCode;

    @Column(name = "in_stock")
    private int inStock;

    @Column(name = "low_stock")
    private int lowStock;

    @Column(name = "average_cost")
    private double averageCost;

    public Item(){

    }
    public Item( String itemName, String itemCode, int inStock, int lowStock, double averageCost) {

        this.itemName = itemName;
        this.itemCode = itemCode;
        this.inStock = inStock;
        this.lowStock = lowStock;
        this.averageCost = averageCost;
    }

    public void setNewItem(){
        this.itemName = "";
        this.itemCode = "";
        this.inStock = 0;
        this.lowStock = 0;
        this.averageCost = 0.0;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getLowStock() {
        return lowStock;
    }

    public void setLowStock(int lowStock) {
        this.lowStock = lowStock;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
    }
}
