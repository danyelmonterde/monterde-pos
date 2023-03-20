package com.monterdev.monterdepos.model;


import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "in_stock")
    private int inStock;

    @Column(name = "low_stock")
    private int lowStock;

    @Column(name = "average_cost")
    private double averageCost;

    public Item(){

    }
    public Item(int id, String itemName, String itemCode, int inStock, int lowStock, double averageCost) {
        this.id = id;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.inStock = inStock;
        this.lowStock = lowStock;
        this.averageCost = averageCost;
    }

    public void setNewItem(){
        this.id = 0;
        this.itemName = "";
        this.itemCode = "";
        this.inStock = 0;
        this.lowStock = 0;
        this.averageCost = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
