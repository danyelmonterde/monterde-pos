package com.monterdev.monterdepos.components;

import com.monterdev.monterdepos.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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
    protected TextField originalPrice;

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

    @FXML
    protected RadioButton radioNewItem;

    @FXML
    protected RadioButton radioExistingItem;

    @FXML
    protected DatePicker itemExpirationDate;

    @FXML
    protected TextField expirationTagNumber;

    @FXML
    protected Label expirationTagDateLabel;

    @FXML
    protected  Label expirationTagLabel;

    protected void clearFields() {
        itemName.setText("");
        itemQuantity.setText("");
        itemLowStock.setText("");
        itemInStock.setText("");
        originalPrice.setText("");
        itemAverageCost.setText("");
        isDiscountable.setSelected(false);
        itemSupplierName.setText("");
        itemSupplierLocation.setText("");
        radioExistingItem.setSelected(false);
        radioNewItem.setSelected(false);
        expirationTagNumber.setText("");
    }

    protected void disableFields() {
        itemName.setDisable(true);
        itemQuantity.setDisable(true);
        itemLowStock.setDisable(true);
        itemInStock.setDisable(true);
        originalPrice.setDisable(true);
        itemAverageCost.setDisable(true);
        isDiscountable.setDisable(true);
        itemCategory.setDisable(true);
        itemSupplierName.setDisable(true);
        itemSupplierLocation.setDisable(true);
        dateBoughtFromSupplier.setDisable(true);
        itemExpirationDate.setDisable(true);
        expirationTagNumber.setDisable(true);
    }

    protected void enableFields() {
        itemName.setDisable(false);
        itemQuantity.setDisable(false);
        itemLowStock.setDisable(false);
        itemInStock.setDisable(false);
        originalPrice.setDisable(false);
        itemAverageCost.setDisable(false);
        isDiscountable.setDisable(false);
        itemCategory.setDisable(false);
        itemSupplierName.setDisable(false);
        itemSupplierLocation.setDisable(false);
        dateBoughtFromSupplier.setDisable(false);
        itemExpirationDate.setDisable(false);
        expirationTagNumber.setDisable(false);
    }

    @FXML
    private void quantityOnly(KeyEvent keyEvent) {
        if(!NumberUtils.isDigits(itemQuantity.getText())){
            itemQuantity.setText(StringUtil.numbersOnly(itemQuantity.getText()));
            itemQuantity.positionCaret(itemQuantity.getLength());
        }
    }

    @FXML
    private void lowStockOnly(KeyEvent keyEvent) {
        if(!NumberUtils.isDigits(itemLowStock.getText())){
            itemLowStock.setText(StringUtil.numbersOnly(itemLowStock.getText()));
            itemLowStock.positionCaret(itemLowStock.getLength());
        }
    }

    @FXML
    private void inStockOnly(KeyEvent keyEvent) {
        if(!NumberUtils.isDigits(itemInStock.getText())){
            itemInStock.setText(StringUtil.numbersOnly(itemInStock.getText()));
            itemInStock.positionCaret(itemInStock.getLength());
        }
    }

    @FXML
    private void averageCostOnly(KeyEvent keyEvent) {
        if(!NumberUtils.isDigits(itemAverageCost.getText())){
            itemAverageCost.setText(StringUtil.numbersOnly(itemAverageCost.getText()));
            itemAverageCost.positionCaret(itemAverageCost.getLength());
        }
    }

    @FXML
    private void originalPriceOnly(KeyEvent keyEvent) {
        if(!NumberUtils.isDigits(originalPrice.getText())){
            originalPrice.setText(StringUtil.numbersOnly(originalPrice.getText()));
            originalPrice.positionCaret(originalPrice.getLength());
        }
    }
}
