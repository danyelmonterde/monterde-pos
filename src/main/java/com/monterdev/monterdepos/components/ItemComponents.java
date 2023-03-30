package com.monterdev.monterdepos.components;

import com.monterdev.monterdepos.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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

    private StringBuilder digitsOnly = new StringBuilder();

    protected void clearFields(){
        itemName.setText("");
        itemQuantity.setText("");
        itemLowStock.setText("");
        itemInStock.setText("");
        itemAverageCost.setText("");
        isDiscountable.setSelected(false);
        itemSupplierName.setText("");
        itemSupplierLocation.setText("");
    }

    protected void disableFields(){
        itemName.setDisable(true);
        itemQuantity.setDisable(true);
        itemLowStock.setDisable(true);
        itemInStock.setDisable(true);
        itemAverageCost.setDisable(true);
        isDiscountable.setDisable(true);
        itemCategory.setDisable(true);
        itemSupplierName.setDisable(true);
        itemSupplierLocation.setDisable(true);
        dateBoughtFromSupplier.setDisable(true);
    }

    protected void enableFields(){
        itemName.setDisable(false);
        itemQuantity.setDisable(false);
        itemLowStock.setDisable(false);
        itemInStock.setDisable(false);
        itemAverageCost.setDisable(false);
        isDiscountable.setDisable(false);
        itemCategory.setDisable(false);
        itemSupplierName.setDisable(false);
        itemSupplierLocation.setDisable(false);
        dateBoughtFromSupplier.setDisable(false);
    }

    @FXML
    private void quantityOnly(KeyEvent keyEvent) {
        itemQuantity.setText(StringUtil.numbersOnly(itemQuantity.getText()));
    }
}
