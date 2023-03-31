package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.CategoryDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.dao.PurchaseDao;
import com.monterdev.monterdepos.dao.PurchaseTransactionDao;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Purchase;
import com.monterdev.monterdepos.model.PurchaseTransaction;
import com.monterdev.monterdepos.util.Prompt;
import javafx.fxml.FXML;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class UpdateItemService extends DeleteItemService {

    private ItemDao itemDao;

    private PurchaseDao purchaseDao;

    private PurchaseTransactionDao purchaseTransactionDao;

    private CategoryDao categoryDao;

    @FXML
    private void updateItem() {
        itemDao = ItemDao.getInstance();
        purchaseDao = PurchaseDao.getInstance();
        purchaseTransactionDao = PurchaseTransactionDao.getInstance();
        categoryDao = CategoryDao.getInstance();

        Item item = new Item();
        item.setItemName(super.itemName.getText());
        item.setInStock(Integer.parseInt(super.itemInStock.getText()));
        item.setLowStock(Integer.parseInt(super.itemLowStock.getText()));
        item.setOriginalPrice(Double.parseDouble(super.originalPrice.getText()));
        item.setAverageCost(Double.parseDouble(super.itemAverageCost.getText()));
        item.setDiscountable(super.isDiscountable.selectedProperty().get());
        item.setItemCode(super.itemCode.getText());

        Category category = categoryDao.getCategoryByName(String.valueOf(super.itemCategory.getSelectionModel().getSelectedItem()));
        item.setCategoryId(category.getId());

        List<Purchase> purchaseList = purchaseDao.getPurchaseListByItemCode(super.itemCode.getText());
        PurchaseTransaction purchaseTransaction = purchaseTransactionDao.getPurchaseTransactionByTransactionNumber(purchaseList.get(0).getTransactionNumber());

        Purchase purchase = new Purchase();
        purchase.setCost(Double.parseDouble(super.itemAverageCost.getText()));
        purchase.setItemCode(super.itemCode.getText());
        int quantity = Integer.parseInt(super.itemInStock.getText());
        double cost = Double.parseDouble(super.itemAverageCost.getText());
        purchase.setTotal(quantity * cost);
        purchase.setQuantity(quantity);
        purchase.setDateBoughtFromSupplier(Date.from(super.dateBoughtFromSupplier.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        purchase.setTransactionNumber(purchaseTransaction.getTransactionNumber());

        PurchaseTransaction purchaseTransactionUpdate = new PurchaseTransaction();
        purchaseTransactionUpdate.setGrandTotal(quantity * cost);
        purchaseTransactionUpdate.setTotalItems(quantity);
        purchaseTransactionUpdate.setSupplier(super.itemSupplierName.getText());
        purchaseTransactionUpdate.setSupplierLocation(super.itemSupplierLocation.getText());
        purchaseTransactionUpdate.setDateTransacted(new Date());
        purchaseTransactionUpdate.setTransactionNumber(purchaseTransaction.getTransactionNumber());


        itemDao.updateItem(item);
        purchaseDao.updatePurchase(purchase);
        purchaseTransactionDao.updatePurchaseTransaction(purchaseTransactionUpdate);


        Prompt.success("Product was updated!");
        clearFields();
        super.itemCode.setText("");
        super.itemCode.requestFocus();
        super.itemCode.setDisable(false);
        super.btnUpdateItem.setVisible(false);
        super.btnCancel.setVisible(false);
        super.itemQuantity.setDisable(false);

    }

}
