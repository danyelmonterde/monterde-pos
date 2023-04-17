package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.BatchImportComponent;
import com.monterdev.monterdepos.dao.ExpirationTagDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.dao.PurchaseDao;
import com.monterdev.monterdepos.dao.PurchaseTransactionDao;
import com.monterdev.monterdepos.model.*;
import com.monterdev.monterdepos.util.ExpirationTagGenerator;
import com.monterdev.monterdepos.util.Prompt;
import com.monterdev.monterdepos.util.TransactionNumberGenerator;
import com.opencsv.bean.CsvToBeanBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BatchImportService extends BatchImportComponent implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(BatchImportService.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void importItems() {

        ItemDao itemDao = ItemDao.getInstance();
        PurchaseDao purchaseDao = PurchaseDao.getInstance();
        PurchaseTransactionDao purchaseTransactionDao = PurchaseTransactionDao.getInstance();
        ExpirationTagDao expirationTagDao = ExpirationTagDao.getInstance();

        Stage stage = (Stage) btnImport.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Grocery Items to Database");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                List<BatchItems> importedItems = new CsvToBeanBuilder(new FileReader(file))
                        .withType(BatchItems.class)
                        .build().parse();

                if (!importedItems.isEmpty()) {
                    if (Prompt.confirm("Are you sure you want to import this file? Existing items will be deleted.").get().getText().equalsIgnoreCase("OK")) {
                        importedItems.stream().forEach(e -> {
                            Item item = new Item();
                            item.setItemName(e.getItemName());
                            item.setItemCode(e.getBarCode());
                            item.setInStock(e.getQuantity());
                            item.setOriginalPrice(e.getUnitPrice());
                            item.setAverageCost(e.getSellingPrice());
                            item.setCategoryId(1);
                            item.setDiscountable(true);
                            item.setLowStock(10);


                            Purchase purchase = new Purchase();
                            purchase.setCost(e.getUnitPrice());
                            purchase.setTotal(e.getTotalPrice());
                            purchase.setQuantity(e.getQuantity());
                            String transactionNumber = TransactionNumberGenerator.generateTransactionNumber();
                            purchase.setTransactionNumber(transactionNumber);
                            purchase.setItemCode(e.getBarCode());
                            purchase.setDateBoughtFromSupplier(new Date());

                            PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
                            purchaseTransaction.setTransactionNumber(transactionNumber);
                            purchaseTransaction.setTotalItems(e.getQuantity());
                            purchaseTransaction.setGrandTotal(e.getTotalPrice());
                            purchaseTransaction.setSupplier("SUY SING");
                            purchaseTransaction.setSupplierLocation("MEYCAUAYAN BULACAN");
                            purchaseTransaction.setDateTransacted(new Date());

                            ExpirationTag expirationTag = new ExpirationTag();
                            expirationTag.setItemCode(e.getBarCode());
                            expirationTag.setExpirationTag(ExpirationTagGenerator.generateExpirationTag());

                            String strExpirationDate = e.getDateOfExpiration();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                            LocalDate expirationDate = LocalDate.parse(strExpirationDate, formatter);
                            expirationTag.setDateOfExpiration(expirationDate);
                            expirationTag.setItemCount(e.getQuantity());


                            itemDao.saveItem(item);
                            LOGGER.info("IMPORTING ITEM: "+item.getItemName());
                            purchaseDao.savePurchase(purchase);
                            purchaseTransactionDao.savePurchaseTransaction(purchaseTransaction);
                            expirationTagDao.saveExpirationTag(expirationTag);


                        });
                        Prompt.success("Items were successfully imported!");
                        LOGGER.info("DONE IMPORTING ITEMS AND OTHER TRANSACTIONS");
                        stage.close();
                    } else {
                        Prompt.failed("Error importing Items!");
                    }
                }

            } catch (Exception exception) {
                Prompt.failed("An error occurred while importing data!");
            }
        }
    }
}
