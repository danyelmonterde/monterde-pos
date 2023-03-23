package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.DashboardDao;
import com.monterdev.monterdepos.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.security.Key;
import java.util.List;

public class SearchService extends DescriptionService {

    private ObservableList<String> listOfItems;

    @FXML
    public void searchItemFromItemList(KeyEvent e) {

        if(e.getCode() == KeyCode.END){
            amountPaid.requestFocus();
        }
        else if(e.getCode() == KeyCode.HOME){
            cart.requestFocus();
        }
        else if(e.getCode() == KeyCode.RIGHT){
            quantity.requestFocus();
        }


        listOfItems = FXCollections.observableArrayList();

        DashboardDao dashboardDao = new DashboardDao();
        List<Item> itemList = dashboardDao.getItems(super.searchItem.getText());
        itemList.stream().forEach(s -> {
            listOfItems.add(s.getItemCode());

        });
        itemListView.setItems(listOfItems);

        if (itemListView.getItems().size() > 0) {
            if (itemListView.getItems().get(0).equals(searchItem.getText())) {
                itemListView.getSelectionModel().select(0);
                searchItemByItemCode();
                searchItem.clear();
            }
        }


    }

}
