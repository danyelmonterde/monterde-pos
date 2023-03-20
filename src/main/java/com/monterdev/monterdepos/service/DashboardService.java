package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.DashboardDao;
import com.monterdev.monterdepos.model.Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.util.List;

public class DashboardService {

    private ObservableList<String> listOfItems;
    @FXML
    private ListView itemListView;

    @FXML
    private TextField searchItem;



    protected void initializeItemList() {


    }

    @FXML
    public void searchItemFromItemList() {
        listOfItems = FXCollections.observableArrayList();

        DashboardDao dashboardDao = new DashboardDao();
        List<Item> itemList = dashboardDao.getItems(searchItem.getText());
        itemList.stream().forEach(s->{
            listOfItems.add(s.getItemName());

        });
        itemListView.setItems(listOfItems);
    }


}
