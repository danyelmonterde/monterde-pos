package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.DashboardComponents;
import com.monterdev.monterdepos.dao.ExpirationTagDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.dao.SalesDao;
import com.monterdev.monterdepos.dao.TransactionDao;
import com.monterdev.monterdepos.exception.POSException;
import com.monterdev.monterdepos.model.ExpirationTag;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Sales;
import com.monterdev.monterdepos.model.SalesTransaction;
import com.monterdev.monterdepos.util.Prompt;
import com.monterdev.monterdepos.util.ReceiptPrinter;
import com.monterdev.monterdepos.util.StringUtil;
import com.monterdev.monterdepos.util.TransactionNumberGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.monterdev.monterdepos.constants.CartLogs.*;
import static com.monterdev.monterdepos.constants.POSSymbols.CART_TEXT_SEPARATOR;
import static com.monterdev.monterdepos.constants.POSSymbols.CART_TEXT_SEPARATOR_REGEX;

public class CartService extends DashboardComponents {

    private ObservableSet<String> itemsOnCartSet = FXCollections.observableSet();
    private static final String PCS = " PCS";
    private static final Logger LOGGER = LogManager.getLogger(CartService.class);
    private int selectedIndex = 0;
    private double total = 0.0;
    private ItemDao itemDao;

    private SalesDao salesDao;

    private TransactionDao transactionDao;


    @FXML
    public void addItemToCart() {
        quantity.setDisable(true);
        btnAddItem.setVisible(false);
        btnCancelItem.setVisible(false);
        amountPaid.setDisable(false);
        ObservableList<String> itemsOnCartArrayList = FXCollections.observableArrayList();
        try {
            if (inStock.getText() == "0" || (Integer.parseInt(quantity.getText()) > Integer.parseInt(inStock.getText()))) {
                LOGGER.error(NO_STOCK_AVAILABLE);
            } else if(Integer.parseInt(quantity.getText())>0){
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
                    searchItem.requestFocus();
                    searchItem.setText("");
                    resetSelectedItem();
                }

            }else{
                quantity.setDisable(false);
                btnAddItem.setVisible(true);
                btnCancelItem.setVisible(true);
                Prompt.failed("Enter quantity greater than 0!");
            }
        } catch (NumberFormatException emptyQuantity) {
            LOGGER.error(new POSException(NOT_A_NUMBER, emptyQuantity.getCause()));
        }

    }

    private void disableAmountPaidIfCartIsEmpty(){
        if(cart.getItems().size()==0){
            amountPaid.setDisable(true);
        }
    }

    @FXML
    public void selectedItemFromCart(KeyEvent e) {
        try {
            quantity.setDisable(false);
            btnAddItem.setVisible(true);
            btnCancelItem.setVisible(true);
            selectedIndex = cart.getSelectionModel().getSelectedIndex();
            String selectedText = (String) cart.getSelectionModel().getSelectedItem();
            String itemCode = selectedText.split(CART_TEXT_SEPARATOR_REGEX)[0];
            String quantity = selectedText.split(CART_TEXT_SEPARATOR_REGEX)[2];
            itemDao = ItemDao.getInstance();
            Item item = itemDao.getItemByItemCode(itemCode);
            if (e.getCode() == KeyCode.DELETE && (!cart.getItems().isEmpty())) {

                //REMOVE ITEM FROM CART AND DEDUCT FROM TOTAL
                super.quantity.setVisible(true);
                cart.getItems().remove(selectedIndex);
                disableAmountPaidIfCartIsEmpty();
                if(cart.getItems().size()==0){
                    searchItem.requestFocus();
                    super.quantity.setVisible(false);
                    btnAddItem.setVisible(false);
                    btnCancelItem.setVisible(false);
                }
                itemsOnCartSet.remove(selectedText);
                total -= (item.getAverageCost() * Integer.parseInt(quantity));
                super.grandTotal.setText(String.valueOf(total));
                super.change.setText("0");
                LOGGER.info(ITEM_REMOVED_FROM_CART);
            } else if (e.getCode() == KeyCode.ENTER) {
                //UPDATE SELECTED ITEM TO DESCRIPTION CONTAINER, REMOVE IT FROM CART AND DEDUCT IT FROM TOTAL
                super.quantity.setVisible(true);
                super.quantity.requestFocus();
                cart.getItems().remove(selectedIndex);
                disableAmountPaidIfCartIsEmpty();
                itemsOnCartSet.remove(selectedText);
                total -= (item.getAverageCost() * Integer.parseInt(quantity));
                super.change.setText("0");
                super.grandTotal.setText(String.valueOf(total));
                super.itemCode.setText(itemCode);
                super.itemName.setText(item.getItemName());
                super.quantity.setText(quantity);
                super.inStock.setText(String.valueOf(item.getInStock()));
                super.lowStock.setText(String.valueOf(item.getLowStock()));
                super.averageCost.setText(String.valueOf(item.getAverageCost()));
            } else if (e.getCode() == KeyCode.ESCAPE) {
                searchItem.requestFocus();
                super.quantity.setVisible(false);
                btnCancelItem.setVisible(false);
                btnAddItem.setVisible(false);
                super.amountPaid.setText("0");
                super.change.setText("0");
            } else if (e.getCode() == KeyCode.END) {
                super.quantity.setVisible(false);
                btnCancelItem.setVisible(false);
                btnAddItem.setVisible(false);
                amountPaid.requestFocus();

            }
        } catch (NullPointerException n) {
            LOGGER.error(new POSException(n.getMessage(), n.getCause()));
        }

    }

    @FXML
    public void computeForChange(KeyEvent e) {
        if(!NumberUtils.isDigits(amountPaid.getText())){
            amountPaid.setText(StringUtil.numbersOnly(amountPaid.getText()));
            amountPaid.positionCaret(amountPaid.getLength());
        }
        double grandTotal = Double.parseDouble(super.grandTotal.getText());
        double amountPaid = Double.parseDouble(super.amountPaid.getText());
        double change = amountPaid - grandTotal;
        super.change.setText(String.valueOf(change));
        if (e.getCode() == KeyCode.ESCAPE) {
            searchItem.requestFocus();
            super.amountPaid.setText("0");
            super.change.setText("0");
        } else if (e.getCode() == KeyCode.CONTROL) {
            quantity.requestFocus();
        } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.HOME) {
            btnAddItem.setVisible(false);
            btnCancelItem.setVisible(false);
            super.quantity.setVisible(false);
            super.change.setText("0");
            super.amountPaid.setText("0");
            cart.requestFocus();
        } else if (e.getCode() == KeyCode.ENTER) {
            checkout();
        }
    }

    @FXML
    public void enterItemToCartFromQuantity(KeyEvent e) {
        try {
            if(!NumberUtils.isDigits(quantity.getText())){
                quantity.setText(StringUtil.numbersOnly(quantity.getText()));
                quantity.positionCaret(quantity.getLength());
            }
            if (e.getCode() == KeyCode.ENTER && (Integer.parseInt(quantity.getText()) > 0)) {
                ExpirationTag expirationTag = ExpirationTagDao.getInstance().getExpirationTagById(expirationTagNumber.getText());
                LocalDate currentDateMinus30Days = LocalDate.now().minusDays(30);
                if(expirationTag.getDateOfExpiration().isBefore(currentDateMinus30Days)){
                    Prompt.failed("Item is about to expire! Item will not be sold!");
                    resetSelectedItem();
                }else{
                    addItemToCart();
                }

            } else if (e.getCode() == KeyCode.ESCAPE) {
                searchItem.requestFocus();
                amountPaid.setText("0");
                change.setText("0");
            } else if (e.getCode() == KeyCode.END) {
                amountPaid.requestFocus();
            } else if (e.getCode() == KeyCode.HOME) {
                cart.requestFocus();
            }
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error(new POSException(NOT_A_NUMBER, numberFormatException.getCause()));
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

    private void resetCart() {
        cart.getItems().clear();
        itemsOnCartSet.clear();
    }

    public void checkout() {

        Optional<ButtonType> transactConfirmation = Prompt.confirm("Are you sure you want to continue?");
        if (transactConfirmation.isPresent()) {
            if (transactConfirmation.get().getText().equals("OK")) {
                //printReceipt();
                SalesTransaction systemSalesTransaction = new SalesTransaction();
                String transactionNumber = TransactionNumberGenerator.generateTransactionNumber();
                systemSalesTransaction.setTransactionNumber(transactionNumber);
                systemSalesTransaction.setDateTransacted(new Date());

                List<Double> priceOfItemsInCart = new ArrayList<>();
                cart.getItems().stream().forEach(data -> {
                    double sumOfItemsInCart = 0.0;
                    String item_code = data.toString().split(CART_TEXT_SEPARATOR_REGEX)[0];
                    String item_quantity = data.toString().split(CART_TEXT_SEPARATOR_REGEX)[2];
                    itemDao = ItemDao.getInstance();

                    Item item = itemDao.getItemByItemCode(item_code);

                    int in_stock = item.getInStock();
                    item.setInStock(in_stock - Integer.parseInt(item_quantity));
                    sumOfItemsInCart = item.getAverageCost() * Integer.parseInt(item_quantity);
                    priceOfItemsInCart.add(sumOfItemsInCart);
                    itemDao.updateItem(item);

                    Sales salesTransaction = new Sales();
                    salesTransaction.setTotal(sumOfItemsInCart);
                    salesTransaction.setItemName(item.getItemName());
                    salesTransaction.setQuantity(Integer.parseInt(item_quantity));
                    salesTransaction.setPrice(item.getAverageCost());
                    salesTransaction.setDateTransacted(new Date());
                    salesDao = SalesDao.getInstance();
                    salesTransaction.setTransactionNumber(systemSalesTransaction.getTransactionNumber());
                    salesDao.saveSalesTransaction(salesTransaction);


                    LOGGER.info("Item and Sales Transaction was saved!");
                });
                double grandTotal = priceOfItemsInCart.stream()
                        .reduce(0.0, Double::sum);
                systemSalesTransaction.setTotalItems(cart.getItems().size());
                systemSalesTransaction.setGrandTotal(grandTotal);
                systemSalesTransaction.setMoneyChange(Double.parseDouble(change.getText()));
                systemSalesTransaction.setDiscount(0.0);
                systemSalesTransaction.setAmountPaid(Double.parseDouble(amountPaid.getText()));

                transactionDao = TransactionDao.getInstance();
                transactionDao.saveTransaction(systemSalesTransaction);

                resetCart();
                resetSelectedItem();
                resetTransaction();
                searchItem.requestFocus();
                Prompt.success("Thank you for buying!");
                btnAddItem.setVisible(false);
                btnCancelItem.setVisible(false);
                amountPaid.setDisable(true);
                quantity.setDisable(true);
            }

        }
    }

    @FXML
    private void printReceipt() {
        ReceiptPrinter.getInstance().printReceipt();
    }

    private void resetTransaction() {
        grandTotal.setText("0");
        amountPaid.setText("0");
        change.setText("0");
        total = 0;
    }


}
