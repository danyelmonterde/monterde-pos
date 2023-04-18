package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.MainApplication;
import com.monterdev.monterdepos.dao.*;
import com.monterdev.monterdepos.model.*;
import com.monterdev.monterdepos.util.Prompt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class SearchService extends DescriptionService {

    private ObservableList<String> listOfItems;

    private ItemDao itemDao;

    private static final Logger LOGGER = LogManager.getLogger(SearchService.class);

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
    public void cancelItem(){
        itemName.setText("");
        itemCode.setText("");
        averageCost.setText("");
        quantity.setText("");
        inStock.setText("");
        lowStock.setText("");
        searchItem.setText("");
        btnAddItem.setVisible(false);
        btnCancelItem.setVisible(false);
        quantity.setVisible(false);
        searchItem.requestFocus();
        expirationTagNumber.setText("");
        expirationTagNumber.setVisible(false);
    }

    @FXML
    public void openAddItemMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/AddItem.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 390);
        Stage stage = new Stage();
        stage.setTitle("Add or Edit Item");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openInventoryList() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/Inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 782, 500);
        Stage stage = new Stage();
        stage.setTitle("Inventory List of Grocery Products");
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void openPurchaseList() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/PurchaseList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 782, 500);
        Stage stage = new Stage();
        stage.setTitle("Inventory List of Purchased Products");
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openSalesList() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/SalesList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 782, 500);
        Stage stage = new Stage();
        stage.setTitle("Sales List");
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openBatchImport() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("service/BatchImport.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 782, 500);
        Stage stage = new Stage();
        stage.setTitle("Batch Import Items");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateDatabase(){
        if (Prompt.confirm("Are you sure you want to import data from Global Database?").get().getText().equalsIgnoreCase("OK")) {

            try {
                URL url = new URL("https://www.google.com");
                URLConnection connection = url.openConnection();
                connection.connect();

                CategoryDao categoryDao = CategoryDao.getInstance();
                ExpirationTagDao expirationTagDao = ExpirationTagDao.getInstance();
                //UPDATE CATEGORY
                List<Category> globalCategoryList = categoryDao.getCategoryListFromProd();
                LOGGER.info("Updating Category");
                globalCategoryList.stream().forEach(category->{
                    categoryDao.updateCategory(category);
                });


                //UPDATE ITEM
                List<Item> globalItemList = itemDao.getItemListFromProd();
                LOGGER.info("Updating Item");
                globalItemList.stream().forEach(item->{
                    itemDao.updateItem(item);
                });

                //UPDATE ITEM EXPIRATION
                List<ExpirationTag> expirationTagList = expirationTagDao.getExpirationTagListFromProd();
                LOGGER.info("Updating Item Expiration");
                expirationTagList.stream().forEach(tag->{
                    expirationTagDao.updateExpirationTagFromProd(tag);
                });

                //UPDATE PURCHASE
                PurchaseDao purchaseDao = PurchaseDao.getInstance();
                List<Purchase> purchaseList = purchaseDao.getPurchaseListFromProd();
                LOGGER.info("Updating Purchase");
                purchaseList.stream().forEach(purchase -> {
                    purchaseDao.updatePurchaseFromProd(purchase);
                });

                //UPDATE PURCHASE TRANSACTION
                PurchaseTransactionDao purchaseTransactionDao = PurchaseTransactionDao.getInstance();
                List<PurchaseTransaction> purchaseTransactionList = purchaseTransactionDao.getPurchaseTransactionListFromProd();
                LOGGER.info("Updating Purchase Transaction");
                purchaseTransactionList.stream().forEach(purchaseTransaction->{
                    purchaseTransactionDao.updatePurchaseTransactionFromProd(purchaseTransaction);
                });

                //UPDATE SALES
                SalesDao salesDao = SalesDao.getInstance();
                List<Sales> salesList = salesDao.getSalesListFromProd();
                LOGGER.info("Updating Sales");
                salesList.stream().forEach(sales -> {
                    salesDao.updateSales(sales);
                });

                //UPDATE SALES TRANSACTION
                TransactionDao salesTransactionDao = TransactionDao.getInstance();
                List<SalesTransaction> salesTransactionList = salesTransactionDao.getSalesTransactionListFromProd();
                LOGGER.info("Updating Sales Transaction");
                salesTransactionList.stream().forEach(salesTransaction ->{
                    salesTransactionDao.updateSalesTransaction(salesTransaction);
                });
                LOGGER.info("Done updating database!");
                Prompt.success("Database was successfully updated!");


            } catch (MalformedURLException e) {
                LOGGER.error("No internet Connection!");
                Prompt.failed("Please connect to Internet!");
            }catch (NullPointerException e){
                LOGGER.error("No internet Connection!");
                Prompt.failed("Please connect to Internet!");
            }
            catch (IOException e) {
                LOGGER.error("No internet Connection!");
                Prompt.failed("Please connect to Internet!");
            }

        }

    }

    @FXML
    public void backupDatabase(){
        if (Prompt.confirm("Are you sure you want to backup data to Global Database?").get().getText().equalsIgnoreCase("OK")) {

            try {
                URL url = new URL("https://www.google.com");
                URLConnection connection = url.openConnection();
                connection.connect();

                CategoryDao categoryDao = CategoryDao.getInstance();
                ExpirationTagDao expirationTagDao = ExpirationTagDao.getInstance();
                //UPDATE CATEGORY
                List<Category> globalCategoryList = categoryDao.getCategoryList();
                LOGGER.info("Updating Category");
                globalCategoryList.stream().forEach(category->{
                    categoryDao.updateCategoryInProd(category);
                });


                //UPDATE ITEM
                List<Item> globalItemList = itemDao.getItemList();
                LOGGER.info("Updating Item");
                globalItemList.stream().forEach(item->{
                    itemDao.updateItemInProd(item);
                });

                //UPDATE ITEM EXPIRATION
                List<ExpirationTag> expirationTagList = expirationTagDao.getExpirationTagList();
                LOGGER.info("Updating Item Expiration");
                expirationTagList.stream().forEach(tag->{
                    expirationTagDao.updateExpirationTagInProd(tag);
                });

                //UPDATE PURCHASE
                PurchaseDao purchaseDao = PurchaseDao.getInstance();
                List<Purchase> purchaseList = purchaseDao.getPurchaseList();
                LOGGER.info("Updating Purchase");
                purchaseList.stream().forEach(purchase -> {
                    purchaseDao.updatePurchaseInProd(purchase);
                });

                //UPDATE PURCHASE TRANSACTION
                PurchaseTransactionDao purchaseTransactionDao = PurchaseTransactionDao.getInstance();
                List<PurchaseTransaction> purchaseTransactionList = purchaseTransactionDao.getPurchaseTransactionList();
                LOGGER.info("Updating Purchase Transaction");
                purchaseTransactionList.stream().forEach(purchaseTransaction->{
                    purchaseTransactionDao.updatePurchaseTransactionInProd(purchaseTransaction);
                });

                //UPDATE SALES
                SalesDao salesDao = SalesDao.getInstance();
                List<Sales> salesList = salesDao.getSalesList();
                LOGGER.info("Updating Sales");
                salesList.stream().forEach(sales -> {
                    salesDao.updateSalesInProd(sales);
                });

                //UPDATE SALES TRANSACTION
                TransactionDao salesTransactionDao = TransactionDao.getInstance();
                List<SalesTransaction> salesTransactionList = salesTransactionDao.getSalesTransactionList();
                LOGGER.info("Updating Sales Transaction");
                salesTransactionList.stream().forEach(salesTransaction ->{
                    salesTransactionDao.updateSalesTransactionInProd(salesTransaction);
                });
                LOGGER.info("Done updating database!");
                Prompt.success("Database was successfully updated!");


            } catch (MalformedURLException e) {
                LOGGER.error("No internet Connection!");
                Prompt.failed("Please connect to Internet!");
            }catch (NullPointerException e){
                LOGGER.error("No internet Connection!");
                Prompt.failed("Please connect to Internet!");
            }
            catch (IOException e) {
                LOGGER.error("No internet Connection!");
                Prompt.failed("Please connect to Internet!");
            }

        }
    }

}
