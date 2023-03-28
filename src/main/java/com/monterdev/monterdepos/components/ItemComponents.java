package com.monterdev.monterdepos.components;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ItemComponents {

    @FXML
    protected TextField itemCode;

    @FXML
    protected TextField itemName;

    @FXML
    protected TextField itemQuantity;

    @FXML
    protected TextField itemLowStock;

    @FXML
    protected TextField itemInStock;

    @FXML
    protected TextField itemAverageCost;

    @FXML
    protected TextField itemSupplierName;

    @FXML
    protected TextField itemSupplierLocation;

    @FXML
    protected RadioButton isDiscountable;

    @FXML
    protected ComboBox itemCategory;

    @FXML
    protected DatePicker dateBoughtFromSupplier;

    @FXML
    protected Button btnAddItem;

    @FXML
    protected Button btnUpdateItem;

    @FXML
    protected Button btnDeleteItem;

    @FXML
    protected Button btnCancel;

}
