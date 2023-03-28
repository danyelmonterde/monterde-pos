package com.monterdev.monterdepos.components;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DashboardComponents {

    @FXML
    protected TextField searchItem;
    @FXML
    protected ListView cart;
    @FXML
    protected CheckBox discounted;
    @FXML
    protected TextField grandTotal;
    @FXML
    protected TextField amountPaid;
    @FXML
    protected TextField change;
    @FXML
    protected JFXButton btnReceipt;
    @FXML
    protected Label itemCode;
    @FXML
    protected Label itemName;
    @FXML
    protected Label averageCost;
    @FXML
    protected TextField quantity;
    @FXML
    protected Label inStock;
    @FXML
    protected Label lowStock;
    @FXML
    protected JFXButton btnAddItem;
    @FXML
    protected JFXButton btnCancelItem;

    @FXML
    protected MenuItem AddItemMenu;

}
