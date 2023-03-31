package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.InventoryListComponents;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

public class InventoryService extends InventoryListComponents {

    //LOCAL VARIABLES BELOW HERE
    private ObservableList<Item> itemObservableList;
    private ObservableList<Category> categoryObservableList;
    private List<Item> itemList;
    private int numberOfRowsPerPage = 10; //Initial number of rows per page

    private Item selectedItem;
    private static String CURRENT_SELECTED_CATEGORY = "";
    private static String CURRENT_SELECTED_STOCK_ALERT = "";
    private List<String> stockAlertLists = new ArrayList<>();
    private List<String> categoryLists = new ArrayList<>();

    @FXML
    private void stockAvailability(){

    }

    @FXML
    private void categoryOnChange(){

    }

    @FXML
    private void exportItems(){

    }

    @FXML
    private void tenRows(){

    }

    @FXML
    private void fiftyRows(){

    }

    @FXML
    private void hundredRows(){

    }

    @FXML
    private void thousandRows(){

    }

    @FXML
    private void previousPage(){

    }

    @FXML
    private void nextPage(){

    }

}
