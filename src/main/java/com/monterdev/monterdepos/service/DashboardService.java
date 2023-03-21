package com.monterdev.monterdepos.service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.monterdev.monterdepos.dao.DashboardDao;
import com.monterdev.monterdepos.model.Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class DashboardService {

    private ObservableList<String> listOfItems;
    @FXML
    private ListView itemListView;

    @FXML
    private TextField searchItem;

    @FXML
    private Label itemCode;

    @FXML
    private Label itemName;

    @FXML
    private Label averageCost;

    @FXML
    private TextField quantity;

    @FXML
    private Label inStock;

    @FXML
    private Label lowStock;

    @FXML
    private Label addedBy;

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnCancelItem;

    @FXML
    private ListView cart;

    @FXML
    private CheckBox discounted;

    @FXML
    private JFXTextField grandTotal;

    @FXML
    private JFXTextField amountPaid;

    @FXML
    private JFXTextField change;

    @FXML
    private JFXButton btnReceipt;



    protected void initializeItemList() {


    }

    @FXML
    public void searchItemFromItemList() {
        listOfItems = FXCollections.observableArrayList();

        DashboardDao dashboardDao = new DashboardDao();
        List<Item> itemList = dashboardDao.getItems(searchItem.getText());
        itemList.stream().forEach(s->{
            listOfItems.add(s.getItemCode());

        });
        itemListView.setItems(listOfItems);
    }


}
