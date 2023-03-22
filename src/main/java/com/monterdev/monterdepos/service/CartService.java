package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.DashboardComponents;
import com.monterdev.monterdepos.dao.DashboardDao;
import com.monterdev.monterdepos.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CartService extends DashboardComponents {

    private ObservableSet<String> itemsOnCartSet = FXCollections.observableSet();
    private static final String PCS = " PCS";
    private static final Logger LOGGER = LogManager.getLogger(CartService.class);
    private int selectedIndex = 0;
    private double total = 0.0;

    private static final String NO_STOCK_AVAILABLE = "sorry there's no stock available";

    private static final String ITEM_ADDED_TO_CART = "ok. item added to cart";

    private static final String ITEM_REMOVED_FROM_CART = "Item removed from cart";

    private static final String CART_TEXT_SEPARATOR = "|";
    private static final String CART_TEXT_SEPARATOR_REGEX = "\\|";



    @FXML
    public void addItemToCart() {

        ObservableList<String> itemsOnCartArrayList = FXCollections.observableArrayList();

        if (inStock.getText() == "0" || (Integer.parseInt(quantity.getText()) > Integer.parseInt(inStock.getText()))) {
            LOGGER.error(NO_STOCK_AVAILABLE);
        } else {
            List<String> itemCodesInSet = new ArrayList<>();
            itemsOnCartSet.stream().forEach(data -> {
                itemCodesInSet.add(data.split(CART_TEXT_SEPARATOR_REGEX)[0]);
            });
            if (!itemCodesInSet.contains(super.itemCode.getText())) {
                LOGGER.info(ITEM_ADDED_TO_CART);
                total += (Double.parseDouble(averageCost.getText()) * Integer.parseInt(quantity.getText()));
                itemsOnCartSet.add(itemCode.getText() + CART_TEXT_SEPARATOR + itemName.getText() + CART_TEXT_SEPARATOR + quantity.getText() + CART_TEXT_SEPARATOR + PCS);
                itemsOnCartSet.stream().forEach(e -> {
                    itemsOnCartArrayList.add(e);
                });
                grandTotal.setText(String.valueOf(total));
                cart.setItems(itemsOnCartArrayList);
            }

        }
    }

    @FXML
    public void selectedItemFromCart(KeyEvent e) {
        selectedIndex = cart.getSelectionModel().getSelectedIndex();
        String selectedText = (String) cart.getSelectionModel().getSelectedItem();
        String itemCode = selectedText.split(CART_TEXT_SEPARATOR_REGEX)[0];
        String quantity = selectedText.split(CART_TEXT_SEPARATOR_REGEX)[2];
        DashboardDao dashboardDao = new DashboardDao();
        Item item = dashboardDao.getItemByItemCode(itemCode);
        if (e.getCode() == KeyCode.DELETE && (!cart.getItems().isEmpty())) {
            //REMOVE ITEM FROM CART AND DEDUCT FROM TOTAL
            cart.getItems().remove(selectedIndex);
            itemsOnCartSet.remove(selectedText);
            total -= (item.getAverageCost()*Integer.parseInt(quantity));
            double amountPaid = Double.parseDouble(super.amountPaid.getText());
            double change = amountPaid - total;
            super.grandTotal.setText(String.valueOf(total));
            super.change.setText(String.valueOf(change));
            LOGGER.info(ITEM_REMOVED_FROM_CART);
        } else if (e.getCode() == KeyCode.ENTER) {
            //UPDATE SELECTED ITEM TO DESCRIPTION CONTAINER, REMOVE IT FROM CART AND DEDUCT IT FROM TOTAL
            cart.getItems().remove(selectedIndex);
            itemsOnCartSet.remove(selectedText);
            total -= item.getAverageCost();
            double amountPaid = Double.parseDouble(super.amountPaid.getText());
            double change = amountPaid - total;
            super.change.setText(String.valueOf(change));
            super.grandTotal.setText(String.valueOf(total));
            super.itemCode.setText(itemCode);
            super.itemName.setText(item.getItemName());
            super.quantity.setText(quantity);
            super.inStock.setText(String.valueOf(item.getInStock()));
            super.lowStock.setText(String.valueOf(item.getLowStock()));
            super.averageCost.setText(String.valueOf(item.getAverageCost()));
        }
    }

    @FXML
    public void computeForChange(KeyEvent e) {
        double grandTotal = Double.parseDouble(super.grandTotal.getText());
        double amountPaid = Double.parseDouble(super.amountPaid.getText());
        double change = amountPaid - grandTotal;
        super.change.setText(String.valueOf(change));


    }
}
