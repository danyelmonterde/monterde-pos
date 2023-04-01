package com.monterdev.monterdepos.components;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InventoryListComponents {

    @FXML
    protected ComboBox comboStocks;

    @FXML
    protected ComboBox comboCategory;

    @FXML
    protected Button btnExport;

    @FXML
    protected Button btnTen;

    @FXML
    protected Button btnFifty;

    @FXML
    protected Button btnHundred;

    @FXML
    protected Button btnThousand;

    @FXML
    protected Button btnPrevious;

    @FXML
    protected Button btnNext;

    @FXML
    protected TableView tblItemList;

    @FXML
    protected TableColumn columnItemCode;

    @FXML
    protected TableColumn columnItemName;

    @FXML
    protected TableColumn columnQuantity;

    @FXML
    protected TableColumn columnOriginalPrice;

    @FXML
    protected TableColumn columnSellingPrice;

    @FXML
    protected TableColumn columnLowStock;

    @FXML
    protected TextField txtSearchItem;
}
