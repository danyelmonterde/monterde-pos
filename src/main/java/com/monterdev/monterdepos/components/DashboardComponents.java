package com.monterdev.monterdepos.components;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DashboardComponents {

    @FXML
    public ListView cart;
    @FXML
    public CheckBox discounted;
    @FXML
    public TextField grandTotal;
    @FXML
    public TextField amountPaid;
    @FXML
    public TextField change;
    @FXML
    public JFXButton btnReceipt;
    @FXML
    public Label itemCode;
    @FXML
    public Label itemName;
    @FXML
    public Label averageCost;
    @FXML
    public TextField quantity;
    @FXML
    public Label inStock;
    @FXML
    public Label lowStock;
    @FXML
    public Label addedBy;
    @FXML
    public JFXButton btnAddItem;
    @FXML
    public JFXButton btnCancelItem;
}
