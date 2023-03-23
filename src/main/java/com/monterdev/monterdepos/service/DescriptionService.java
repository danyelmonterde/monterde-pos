package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.dao.DashboardDao;
import com.monterdev.monterdepos.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DescriptionService extends CartService {

    @FXML
    public ListView itemListView;

    private DashboardDao dashboardDao;

    @FXML
    public void searchItemByItemCode() {
        quantity.requestFocus();
        String selectedItemCode = String.valueOf(itemListView.getSelectionModel().getSelectedItem());
        itemCode.setText(selectedItemCode);
        dashboardDao = DashboardDao.getInstance();
        Item item = dashboardDao.getItemByItemCode(selectedItemCode);
        itemName.setText(item.getItemName());
        averageCost.setText(String.valueOf(item.getAverageCost()));
        inStock.setText(String.valueOf(item.getInStock()));
        boolean isLowStock = (item.getInStock() <= item.getLowStock());
        if (isLowStock) {
            lowStock.setText("Yes");
        } else {
            lowStock.setText("No");
        }
        searchItem.clear();


    }


}
