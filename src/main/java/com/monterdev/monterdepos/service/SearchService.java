package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.MainApplication;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SearchService extends DescriptionService {

    private ObservableList<String> listOfItems;

    private ItemDao itemDao;

    @FXML
    public void searchItemFromItemList(KeyEvent e) {

        if (e.getCode() == KeyCode.END) {
            amountPaid.requestFocus();
        } else if (e.getCode() == KeyCode.HOME && cart.getItems().size()>0) {
            cart.requestFocus();
            cart.getSelectionModel().select(0);
        } else if (e.getCode() == KeyCode.CONTROL) {
            quantity.requestFocus();
        }


        listOfItems = FXCollections.observableArrayList();

        itemDao = ItemDao.getInstance();
        List<Item> itemList = itemDao.getItemsByItemCode(super.searchItem.getText(),0,10);
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

    @FXML
    public void openAddItemMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/AddItem.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 390);
        Stage stage = new Stage();
        stage.setTitle("Add or Edit Item");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openInventoryList() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/Inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 782, 390);
        Stage stage = new Stage();
        stage.setTitle("Inventory List of Grocery Products");
        stage.setScene(scene);
        stage.show();
    }

}
