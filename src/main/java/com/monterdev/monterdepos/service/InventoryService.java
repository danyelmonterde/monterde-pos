package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.InventoryListComponents;
import com.monterdev.monterdepos.constants.CategoryTypes;
import com.monterdev.monterdepos.constants.StockAlertTypes;
import com.monterdev.monterdepos.dao.CategoryDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryService extends InventoryListComponents implements Initializable {

    private int BEGIN_INDEX = 0; //Initial number of rows per page
    private int END_INDEX = 10; //Initial number of rows per page

    private Item selectedItem;
    private static String DEFAULT_SELECTED_CATEGORY = CategoryTypes.ALL_CATEGORY;
    private static String DEFAULT_SELECTED_STOCK_ALERT = StockAlertTypes.ALL_STOCKS;

    private CategoryDao categoryDao;

    private ItemDao itemDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryDao = CategoryDao.getInstance();
        itemDao = ItemDao.getInstance();
        List<Category> categoryLists = categoryDao.getCategoryList();
        categoryLists.stream().forEach(e->{
            super.comboCategory.getItems().add(e.getCategory());
        });
        super.comboStocks.getItems().add(StockAlertTypes.ALL_STOCKS);
        super.comboStocks.getItems().add(StockAlertTypes.LOW_STOCK);
        super.comboStocks.getItems().add(StockAlertTypes.NO_STOCK);

    }

    @FXML
    private void stockAvailability(){

    }

    @FXML
    private void categoryOnChange(){
        String selectedCategory = (String) comboCategory.getSelectionModel().getSelectedItem();
        String selectedStockAlertType = (String) comboStocks.getSelectionModel().getSelectedItem();
        Category category = categoryDao.getCategoryByName(selectedCategory);
        List<Item> itemList = itemDao.getItemsByCategoryStockAndPaging(category.getId(),selectedStockAlertType,BEGIN_INDEX,END_INDEX);
        itemList.stream().forEach(data->{
            System.out.println("Item name: "+data.getItemName());
        });
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
