package com.monterdev.monterdepos.model;


import java.util.Date;
import java.util.List;

public class POSInstance {

    private Item selectedItem;
    private List<Item> itemList;
    private List<Item> cart;
    private double total;
    private double amountPaid;
    private double change;
    private double discount;
    private Date dateTransacted;
    private String systemUser;

    public POSInstance() {

    }

    public void reset() {
        this.selectedItem.setNewItem();
        this.itemList.clear();
        this.cart.clear();
        this.total = 0.0;
        this.amountPaid = 0.0;
        this.change = 0.0;
        this.discount = 0.0;
        this.dateTransacted = new Date();
    }

    public POSInstance(Item selectedItem, List<Item> itemList, List<Item> cart, double total, double amountPaid, double change, double discount, Date dateTransacted, String systemUser) {
        this.selectedItem = selectedItem;
        this.itemList = itemList;
        this.cart = cart;
        this.total = total;
        this.amountPaid = amountPaid;
        this.change = change;
        this.discount = discount;
        this.dateTransacted = dateTransacted;
        this.systemUser = systemUser;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDateTransacted() {
        return dateTransacted;
    }

    public void setDateTransacted(Date dateTransacted) {
        this.dateTransacted = dateTransacted;
    }

    public String getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(String systemUser) {
        this.systemUser = systemUser;
    }
}
