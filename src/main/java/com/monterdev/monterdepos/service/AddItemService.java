package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.CategoryDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.dao.PurchaseDao;
import com.monterdev.monterdepos.dao.PurchaseTransactionDao;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Purchase;
import com.monterdev.monterdepos.model.PurchaseTransaction;
import com.monterdev.monterdepos.util.ExpirationTagGenerator;
import com.monterdev.monterdepos.util.Prompt;
import com.monterdev.monterdepos.util.StringUtil;
import com.monterdev.monterdepos.util.TransactionNumberGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddItemService extends UpdateItemService implements Initializable {

    private ItemDao itemDao;

    private PurchaseDao purchaseDao;

    private PurchaseTransactionDao purchaseTransactionDao;

    private CategoryDao categoryDao;

    @FXML
    private void searchProductCode(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            itemDao = ItemDao.getInstance();
            purchaseDao = PurchaseDao.getInstance();
            purchaseTransactionDao = PurchaseTransactionDao.getInstance();
            categoryDao = CategoryDao.getInstance();

            List<Category> categoryList = categoryDao.getCategoryList();
            ObservableList<String> categories = FXCollections.observableArrayList();
            categoryList.stream().forEach(category->{
                categories.add(category.getCategory());
            });
            super.itemCategory.setItems(categories);

            String itemCode = super.itemCode.getText();
            Item item = itemDao.getItemByItemCode(itemCode);
            if (item != null) {
                super.btnUpdateItem.setVisible(true);
                super.btnDeleteItem.setVisible(true);
                super.btnCancel.setVisible(true);
                super.btnAddItem.setVisible(false);
                super.radioExistingItem.setSelected(true);
                super.radioNewItem.setSelected(false);

                enableFields();
                super.itemQuantity.setDisable(true);
                super.itemCode.setDisable(true);

                super.itemName.setText(item.getItemName());
                super.itemInStock.setText(String.valueOf(item.getInStock()));
                super.itemLowStock.setText(String.valueOf(item.getLowStock()));
                super.isDiscountable.setSelected(item.isDiscountable());
                super.originalPrice.setText(String.valueOf(item.getOriginalPrice()));
                super.itemAverageCost.setText(String.valueOf(item.getAverageCost()));
                Category category = categoryDao.getCategoryById(item.getCategoryId());
                super.itemCategory.getSelectionModel().select(category.getCategory());

                List<Purchase> purchaseList = purchaseDao.getPurchaseListByItemCode(itemCode);
                PurchaseTransaction purchaseTransaction = purchaseTransactionDao.getPurchaseTransactionByTransactionNumber(purchaseList.get(0).getTransactionNumber());
                super.itemSupplierName.setText(purchaseTransaction.getSupplier());
                super.itemSupplierLocation.setText(purchaseTransaction.getSupplierLocation());
                Date date = purchaseList.get(0).getDateBoughtFromSupplier();
                super.dateBoughtFromSupplier.setValue(date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

            }else if(!StringUtil.cleanString(super.itemCode.getText()).isEmpty()){
                clearFields();
                super.radioNewItem.setSelected(true);
                super.radioExistingItem.setSelected(false);
                super.btnAddItem.setVisible(true);
                enableFields();
                super.itemInStock.setDisable(true);
                super.itemName.requestFocus();
            }
        }else if(super.itemCode.getText().isEmpty()){
            disableFields();
            clearFields();
            btnAddItem.setVisible(false);
            super.itemCode.setDisable(false);
        }
    }

    @FXML
    private void addItem() {
        try{
            if(!itemCode.getText().isEmpty()
                    && !itemName.getText().isEmpty()
                    && !itemQuantity.getText().isEmpty()
                    && !itemLowStock.getText().isEmpty()
                    && !itemAverageCost.getText().isEmpty()
                    && !originalPrice.getText().isEmpty()){

                itemDao = ItemDao.getInstance();
                purchaseDao = PurchaseDao.getInstance();
                purchaseTransactionDao = PurchaseTransactionDao.getInstance();

                super.itemInStock.setDisable(true);

                Item item = new Item();
                item.setItemName(super.itemName.getText());
                item.setInStock(Integer.parseInt(super.itemQuantity.getText()));
                item.setLowStock(Integer.parseInt(super.itemLowStock.getText()));
                item.setOriginalPrice(Double.parseDouble(super.originalPrice.getText()));
                item.setAverageCost(Double.parseDouble(super.itemAverageCost.getText()));
                item.setDiscountable(super.isDiscountable.selectedProperty().get());
                item.setItemCode(super.itemCode.getText());

                Category category = categoryDao.getCategoryByName(String.valueOf(super.itemCategory.getSelectionModel().getSelectedItem()));
                item.setCategoryId(category.getId());


                Purchase purchase = new Purchase();
                purchase.setCost(Double.parseDouble(super.itemAverageCost.getText()));
                purchase.setItemCode(super.itemCode.getText());
                int quantity  = Integer.parseInt(super.itemQuantity.getText());
                double cost = Double.parseDouble(super.itemAverageCost.getText());
                purchase.setTotal(quantity * cost);
                purchase.setQuantity(quantity);
                purchase.setDateBoughtFromSupplier(Date.from(super.dateBoughtFromSupplier.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                String transactionNumber = TransactionNumberGenerator.generateTransactionNumber();
                purchase.setTransactionNumber(transactionNumber);

                PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
                purchaseTransaction.setGrandTotal(quantity * cost);
                purchaseTransaction.setTotalItems(quantity);
                purchaseTransaction.setSupplier(super.itemSupplierName.getText());
                purchaseTransaction.setSupplierLocation(super.itemSupplierLocation.getText());
                purchaseTransaction.setDateTransacted(new Date());
                purchaseTransaction.setTransactionNumber(transactionNumber);

                itemDao.saveItem(item);
                purchaseDao.savePurchase(purchase);
                purchaseTransactionDao.savePurchaseTransaction(purchaseTransaction);

                Prompt.success("Product was saved!");
                clearFields();
                disableFields();
                itemCode.setDisable(false);
                super.itemCode.setText("");
                super.btnAddItem.setVisible(false);
            }else{
                Prompt.failed("Please fill in all fields!");
            }

        }catch (Exception e){
            btnAddItem.setVisible(false);
        }

    }


    @FXML
    private void cancel() {
        super.itemCode.setText("");
        disableFields();
        super.itemCode.setDisable(false);
        super.itemCode.requestFocus();
        clearFields();
        btnAddItem.setVisible(false);
        btnUpdateItem.setVisible(false);
        btnCancel.setVisible(false);
        btnDeleteItem.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String x = ExpirationTagGenerator.generateExpirationTag();
        System.out.println("Expiration Tag Number: "+ x);
    }
}
