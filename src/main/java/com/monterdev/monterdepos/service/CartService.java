package com.monterdev.monterdepos.service;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartService {

    @FXML
    public ListView cart;

    @FXML
    public CheckBox discounted;

    @FXML
    public TextField grandTotal;

    @FXML
    public TextField amountPaid;

    @FXML
    public TextField change;

    @FXML
    public JFXButton btnReceipt;

    @FXML
    public Label itemCode;

    @FXML
    public Label itemName;

    @FXML
    public Label averageCost;

    @FXML
    public TextField quantity;

    @FXML
    public Label inStock;

    @FXML
    public Label lowStock;

    @FXML
    public Label addedBy;

    @FXML
    public JFXButton btnAddItem;

    @FXML
    public JFXButton btnCancelItem;

    private ObservableSet<String> itemsOnCartSet = FXCollections.observableSet();

    private static final String PCS=" PCS";

    private static final Logger LOGGER = LogManager.getLogger(CartService.class);

    private int selectedIndex = 0;

    @FXML
    public void addItemToCart() {
        ObservableList<String> itemsOnCartArrayList = FXCollections.observableArrayList();

        if (inStock.getText() == "0" || (Integer.parseInt(quantity.getText()) > Integer.parseInt(inStock.getText()))) {
            LOGGER.error("sorry there's no stock available");
        } else {
            LOGGER.info("ok. item added to cart");
            itemsOnCartSet.add(itemCode.getText()+"|"+itemName.getText()+"|"+quantity.getText()+PCS);
            itemsOnCartSet.stream().forEach(e->{
                itemsOnCartArrayList.add(e);
            });
            cart.setItems(itemsOnCartArrayList);
        }
    }

    @FXML
    public void selectedItemFromCart(KeyEvent e){
        selectedIndex = cart.getSelectionModel().getSelectedIndex();
        String selectedText = (String) cart.getSelectionModel().getSelectedItem();
        if(e.getCode() == KeyCode.DELETE && (!cart.getItems().isEmpty())){
            cart.getItems().remove(selectedIndex);
            itemsOnCartSet.remove(selectedText);
            LOGGER.info("Item removed from cart");
        }else if(e.getCode() == KeyCode.ENTER){

        }
    }
}
