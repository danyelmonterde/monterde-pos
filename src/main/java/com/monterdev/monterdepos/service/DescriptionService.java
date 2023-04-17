package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.ExpirationTagDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.ExpirationTag;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.util.Prompt;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.time.LocalDate;

public class DescriptionService extends CartService {

    @FXML
    public ListView itemListView;

    private ItemDao itemDao;

    private ExpirationTagDao expirationTagDao;

    @FXML
    public void searchItemByItemCode() {
        expirationTagDao = ExpirationTagDao.getInstance();
        quantity.setDisable(false);
        btnAddItem.setVisible(true);
        btnCancelItem.setVisible(true);
        expirationTagNumber.setVisible(true);
        quantity.setVisible(true);
        quantity.requestFocus();
        String selectedItemCode = String.valueOf(itemListView.getSelectionModel().getSelectedItem());
        itemCode.setText(selectedItemCode);
        ExpirationTag expirationTag = expirationTagDao.getExpirationTagByItemCode(selectedItemCode);
        itemDao = ItemDao.getInstance();
        Item item = itemDao.getItemByItemCode(selectedItemCode);
        itemName.setText(item.getItemName());
        averageCost.setText(String.valueOf(item.getAverageCost()));
        inStock.setText(String.valueOf(item.getInStock()));
        expirationTagNumber.setText(expirationTag.getExpirationTag());

        boolean isLowStock = (item.getInStock() <= item.getLowStock());
        if (isLowStock) {
            lowStock.setText("Yes");
        } else {
            lowStock.setText("No");
        }
        searchItem.clear();

        LocalDate currentDayMinus30 = LocalDate.now().minusDays(30);
        LocalDate currentDay = LocalDate.now();
        if(expirationTag.getDateOfExpiration().isAfter(currentDayMinus30) && expirationTag.getDateOfExpiration().isBefore(currentDay)){
            Prompt.failed("Item is expiring in less than 30 days!");
            resetSelectedItem();
            btnAddItem.setVisible(false);
            btnCancelItem.setVisible(false);
            searchItem.requestFocus();
        }

    }

    private void resetSelectedItem() {
        super.expirationTagNumber.setText("");
        itemCode.setText("");
        itemName.setText("");
        averageCost.setText("");
        quantity.setText("0");
        inStock.setText("");
        lowStock.setText("");
    }


}
