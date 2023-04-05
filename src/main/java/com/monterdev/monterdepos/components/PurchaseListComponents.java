package com.monterdev.monterdepos.components;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PurchaseListComponents {

    @FXML
    protected TextField txtSearchTransaction;

    @FXML
    protected ComboBox comboCategory;
    @FXML
    protected DatePicker dateFrom;
    @FXML
    protected DatePicker dateUntil;
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
    protected TableView tblPurchaseList;

    @FXML
    protected TableColumn columnTransactionNumber;

    @FXML
    protected TableColumn columnItemCode;

    @FXML
    protected TableColumn columnCost;

    @FXML
    protected TableColumn columnQuantity;

    @FXML
    protected TableColumn columnTotal;

    @FXML
    protected TableColumn columnDateBought;


}
