package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.DashboardComponents;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.dao.SalesTransactionDao;
import com.monterdev.monterdepos.dao.TransactionDao;
import com.monterdev.monterdepos.exception.POSException;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Sales;
import com.monterdev.monterdepos.model.SalesTransaction;
import com.monterdev.monterdepos.util.Prompt;
import com.monterdev.monterdepos.util.TransactionNumberGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CartService extends DashboardComponents implements Printable {

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
    private static final String NOT_A_NUMBER = "Input quantity is not a number";
    private static final int NOT_A_NUMBER_ERROR_CODE = 2001;

    private ItemDao itemDao;

    private SalesTransactionDao salesTransactionDao;

    private TransactionDao transactionDao;


    @FXML
    public void addItemToCart() {

        ObservableList<String> itemsOnCartArrayList = FXCollections.observableArrayList();
        try {
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
                    searchItem.requestFocus();
                    searchItem.setText("");
                    resetSelectedItem();
                }

            }
        } catch (NumberFormatException emptyQuantity) {
            LOGGER.error(new POSException(NOT_A_NUMBER, emptyQuantity.getCause()));
        }

    }

    @FXML
    public void selectedItemFromCart(KeyEvent e) {
        try {
            selectedIndex = cart.getSelectionModel().getSelectedIndex();
            String selectedText = (String) cart.getSelectionModel().getSelectedItem();
            String itemCode = selectedText.split(CART_TEXT_SEPARATOR_REGEX)[0];
            String quantity = selectedText.split(CART_TEXT_SEPARATOR_REGEX)[2];
            itemDao = ItemDao.getInstance();
            Item item = itemDao.getItemByItemCode(itemCode);
            if (e.getCode() == KeyCode.DELETE && (!cart.getItems().isEmpty())) {
                //REMOVE ITEM FROM CART AND DEDUCT FROM TOTAL
                cart.getItems().remove(selectedIndex);
                itemsOnCartSet.remove(selectedText);
                total -= (item.getAverageCost() * Integer.parseInt(quantity));
                super.grandTotal.setText(String.valueOf(total));
                super.change.setText("0");
                LOGGER.info(ITEM_REMOVED_FROM_CART);
            } else if (e.getCode() == KeyCode.ENTER) {
                //UPDATE SELECTED ITEM TO DESCRIPTION CONTAINER, REMOVE IT FROM CART AND DEDUCT IT FROM TOTAL
                super.quantity.requestFocus();
                cart.getItems().remove(selectedIndex);
                itemsOnCartSet.remove(selectedText);
                total -= item.getAverageCost();
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
                super.amountPaid.setText("0");
                super.change.setText("0");
            } else if (e.getCode() == KeyCode.END) {
                amountPaid.requestFocus();
            }
        } catch (NullPointerException n) {
            LOGGER.error(new POSException(n.getMessage(), n.getCause()));
        }

    }

    @FXML
    public void computeForChange(KeyEvent e) {
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
            if (e.getCode() == KeyCode.ENTER && (Integer.parseInt(quantity.getText()) > 0)) {
                addItemToCart();
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
            if(transactConfirmation.get().getText().equals("OK")){
                printReceipt();
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
                    salesTransactionDao = SalesTransactionDao.getInstance();
                    salesTransaction.setTransactionNumber(systemSalesTransaction.getTransactionNumber());
                    salesTransactionDao.saveSalesTransaction(salesTransaction);


                    LOGGER.info("Item and Sales salesTransaction was saved!");
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
            }

        }
    }

    @FXML
    private void printReceipt() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                LOGGER.error(new POSException("Error printing", ex.getCause()));
            }
        }else{
            Prompt.success("No receipt will be printed!");
        }
    }

    private void resetTransaction() {
        grandTotal.setText("0");
        amountPaid.setText("0");
        change.setText("0");
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {
        int r = cart.getItems().size();
        // ImageIcon icon=new ImageIcon("C:UsersccsDocumentsNetBeansProjectsvideo TestPOSInvoicesrcposinvoicemylogo.jpg");
        int result = NO_SUCH_PAGE;
        if (page == 0) {

            Graphics2D g2d = (Graphics2D) g;
            double width = pf.getImageableWidth();
            g2d.translate((int) pf.getImageableX(), (int) pf.getImageableY());

                    //x=9,y=4
            //width=594
            //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));

            try {
                int y = 30;
                int yShift = 15;
                int headerRectHeight = 40;
                int x=5;
                // int headerRectHeighta=40;


                g2d.setFont(new Font("AGENCY FB", Font.TRUETYPE_FONT, 14));
                //g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);y+=yShift+30;
                g2d.drawString("-----------------------------------------------", x, y);
                y += yShift;
                g2d.drawString("         ALEN CAI GROCERY STORE        ", x, y);
                y += yShift;
                g2d.drawString("   JAGUAR CORNER CORONET STREET ", x, y);
                y += yShift;
                g2d.drawString("   FAIRVIEW QUEZON CITY ", x, y);
                y += yShift;
                g2d.drawString("   WWW.FACEBOOK.COM/ACMONTERDESTORE ", x, y);
                y += yShift;
                g2d.drawString("        +639182281576      ", x, y);
                y += yShift;
                g2d.drawString("----------------------------------------------------", x, y);
                y += headerRectHeight;

                g2d.drawString(" ITEM NAME                  PRICE   ", x, y);
                y += yShift;
                g2d.drawString("----------------------------------------------------", x, y);
                y += headerRectHeight;


                for (int s = 0; s < r; s++) {
                    itemDao = ItemDao.getInstance();

                    Item item = itemDao.getItemByItemCode(cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[0]);
                    double sumOfItemsInCart = item.getAverageCost() * Integer.parseInt(cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[2]);
                    g2d.drawString(" " + cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[1].toUpperCase() + "                            ", x, y);
                    y += yShift;
                    g2d.drawString("      " + cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[2].toUpperCase() + " * " + item.getAverageCost(), x, y);
                    g2d.drawString(String.valueOf(sumOfItemsInCart), 160, y);
                    y += yShift;
                }
                g2d.drawString("----------------------------------------------------", x, y);
                y += yShift;
                g2d.drawString(" TOTAL AMOUNT:               " + grandTotal.getText() + "   ", x, y);
                y += yShift;
                g2d.drawString("----------------------------------------------------", x, y);
                y += yShift;
                g2d.drawString(" CASH      :                 " + amountPaid.getText() + "   ", x, y);
                y += yShift;
                g2d.drawString("----------------------------------------------------", x, y);
                y += yShift;
                g2d.drawString(" BALANCE   :                 " + change.getText() + "   ", x, y);
                y += yShift;

                g2d.drawString("*************************************", x, y);
                y += headerRectHeight;
                g2d.drawString("       THANK YOU COME AGAIN            ", x, y);
                y += yShift;
                g2d.drawString("*************************************", x, y);
                y += headerRectHeight;
                g2d.drawString("       SOFTWARE BY:MONTERDEV          ", x, y);
                y += yShift;
                g2d.drawString("   CONTACT: DANIEL@MONTERDEV.COM       ", x, y);
                y += yShift;


            } catch (Exception e) {
                e.printStackTrace();
            }

            result = PAGE_EXISTS;
        }
        return result;
    }
}
