package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Purchase;
import javafx.fxml.FXML;

public class AddItemService extends UpdateItemService {

    private ItemDao itemDao;


    @FXML
    private void addItem() {
        itemDao = ItemDao.getInstance();
        String itemCode = super.itemCode.getText();
        Item item = itemDao.getItemByItemCode(itemCode);
        if (item != null) {

                    super.itemName.setText(item.getItemName());
            super.itemInStock.setText(String.valueOf(item.getInStock()));
            super.itemLowStock.setText(String.valueOf(item.getLowStock()));
            super.itemAverageCost.setText(String.valueOf(item.getAverageCost()));


        }
    }


    @FXML
    private void cancel() {

    }
}
