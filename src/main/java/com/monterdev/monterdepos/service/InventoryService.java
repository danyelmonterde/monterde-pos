package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.InventoryListComponents;
import com.monterdev.monterdepos.constants.CategoryTypes;
import com.monterdev.monterdepos.constants.StockAlertTypes;
import com.monterdev.monterdepos.dao.CategoryDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.util.Prompt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.monterdev.monterdepos.constants.ConfigFile.CONFIG_FILE_NAME;

public class InventoryService extends InventoryListComponents implements Initializable {

    private int BEGIN_INDEX = 0; //Initial number of rows per page
    private int END_INDEX = 1000; //Initial number of rows per page

    private Item selectedItem;
    private static String SELECTED_CATEGORY = CategoryTypes.ALL_CATEGORY;
    private static String SELECTED_STOCK_ALERT = StockAlertTypes.ALL_STOCKS;

    private ObservableList<Item> itemObservableList;

    private CategoryDao categoryDao;

    private ItemDao itemDao;

    private List<Item> itemList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryDao = CategoryDao.getInstance();
        itemDao = ItemDao.getInstance();
        List<Category> categoryLists = categoryDao.getCategoryList();
        categoryLists.stream().forEach(e->{
            super.comboCategory.getItems().add(e.getCategory());
        });
        comboCategory.getItems().add(CategoryTypes.ALL_CATEGORY);
        super.comboStocks.getItems().add(StockAlertTypes.ALL_STOCKS);
        super.comboStocks.getItems().add(StockAlertTypes.LOW_STOCK);
        super.comboStocks.getItems().add(StockAlertTypes.NO_STOCK);

        columnItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        columnSellingPrice.setCellValueFactory(new PropertyValueFactory<>("averageCost"));
        columnLowStock.setCellValueFactory(new PropertyValueFactory<>("lowStock"));
        columnOriginalPrice.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));

    }

    @FXML
    private void stockAvailability(){
        BEGIN_INDEX = 0;
        END_INDEX = 10;
        setTableItem();
    }

    @FXML
    private void categoryOnChange(){
        BEGIN_INDEX = 0;
        END_INDEX = 10;
        setTableItem();
    }

    private void setTableItem(){
        SELECTED_CATEGORY = (String) comboCategory.getSelectionModel().getSelectedItem();
        SELECTED_STOCK_ALERT = (String) comboStocks.getSelectionModel().getSelectedItem();
        Category category = categoryDao.getCategoryByName(SELECTED_CATEGORY);
        int categoryId = 0;
        if(category != null){
            categoryId = category.getId();
        }
        itemList = itemDao.getItemsByCategoryStockAndPaging(categoryId,SELECTED_STOCK_ALERT,BEGIN_INDEX,END_INDEX);
        itemObservableList = FXCollections.observableArrayList();
        itemList.stream().forEach(data->{
            itemObservableList.add(data);
        });

        super.tblItemList.setItems(itemObservableList);
    }

    @FXML
    private void tenRows(){
        END_INDEX = 10;
        setTableItem();
    }

    @FXML
    private void fiftyRows(){
        END_INDEX = 50;
        setTableItem();
    }

    @FXML
    private void hundredRows(){
        END_INDEX = 100;
        setTableItem();
    }

    @FXML
    private void thousandRows(){
        END_INDEX = 1000;
        setTableItem();
    }

    @FXML
    private void exportItems() throws IOException {
        Writer writer = null;
        try{
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd -hh-mm-ss");
            String strDate = dateFormat.format(date);
            Properties properties = new Properties();
            properties.load(InventoryService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
            File file = new File(properties.getProperty("inventory.report.location")+strDate.replaceAll("\\s+","")+".csv");
            writer = new BufferedWriter(new FileWriter(file));
            String header = "Item Code, Item Name, Quantity,Low Stock,Original Price, Selling Price\n";
            writer.write(header);
            for(Item item:itemList){
                String text = item.getItemCode()+","+item.getItemName().replace(",","")+","+item.getInStock()+","+item.getInStock()+","+item.getOriginalPrice()+","+item.getAverageCost()+"\n";
                writer.write(text);
            }
            Prompt.success("File was successfully exported to Desktop with file name of current date and time today!");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }



}
