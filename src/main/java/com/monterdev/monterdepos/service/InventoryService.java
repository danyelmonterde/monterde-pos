package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.InventoryListComponents;
import com.monterdev.monterdepos.constants.CategoryTypes;
import com.monterdev.monterdepos.constants.StockAlertTypes;
import com.monterdev.monterdepos.dao.CategoryDao;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.Category;
import com.monterdev.monterdepos.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.monterdev.monterdepos.constants.ConfigFile.CONFIG_FILE_NAME;

public class InventoryService extends InventoryListComponents implements Initializable {

    private int BEGIN_INDEX = 0; //Initial number of rows per page
    private int END_INDEX = 10; //Initial number of rows per page
    private static String SELECTED_CATEGORY = CategoryTypes.ALL_CATEGORY;
    private static String SELECTED_STOCK_ALERT = StockAlertTypes.ALL_STOCKS;

    private ObservableList<Item> itemObservableList;

    private CategoryDao categoryDao;

    private ItemDao itemDao;

    private List<Item> itemList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryDao = CategoryDao.getInstance();
        itemDao = ItemDao.getInstance();
        List<Category> categoryLists = categoryDao.getCategoryList();
        categoryLists.stream().forEach(e -> {
            super.comboCategory.getItems().add(e.getCategory());
        });
        comboCategory.getItems().add(CategoryTypes.ALL_CATEGORY);
        super.comboStocks.getItems().add(StockAlertTypes.ALL_STOCKS);
        super.comboStocks.getItems().add(StockAlertTypes.LOW_STOCK);
        super.comboStocks.getItems().add(StockAlertTypes.NO_STOCK);

        columnItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        columnSellingPrice.setCellValueFactory(new PropertyValueFactory<>("averageCost"));
        columnLowStock.setCellValueFactory(new PropertyValueFactory<>("lowStock"));
        columnOriginalPrice.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));

        setTableItem();
    }

    @FXML
    private void stockAvailability() {
        BEGIN_INDEX = 0;
        END_INDEX = 10;
        setTableItem();
    }

    @FXML
    private void categoryOnChange() {
        BEGIN_INDEX = 0;
        END_INDEX = 10;
        setTableItem();
    }

    private void setTableItem() {
        SELECTED_CATEGORY = (String) comboCategory.getSelectionModel().getSelectedItem();
        SELECTED_STOCK_ALERT = (String) comboStocks.getSelectionModel().getSelectedItem();
        Category category = categoryDao.getCategoryByName(SELECTED_CATEGORY);
        int categoryId = 0;
        if (category != null) {
            categoryId = category.getId();
        }
        if (SELECTED_STOCK_ALERT == null) {
            SELECTED_STOCK_ALERT = StockAlertTypes.ALL_STOCKS;
        }
        itemList = itemDao.getItemsByNameCategoryStockAndPaging(txtSearchItem.getText(), categoryId, SELECTED_STOCK_ALERT, BEGIN_INDEX, END_INDEX);
        itemObservableList = FXCollections.observableArrayList();
        itemList.stream().forEach(data -> {
            itemObservableList.add(data);
        });

        super.tblItemList.setItems(itemObservableList);
    }

    @FXML
    private void tenRows() {
        END_INDEX = 10;
        setTableItem();
    }

    @FXML
    private void fiftyRows() {
        END_INDEX = 50;
        setTableItem();
    }

    @FXML
    private void hundredRows() {
        END_INDEX = 100;
        setTableItem();
    }

    @FXML
    private void thousandRows() {
        END_INDEX = 10000;
        setTableItem();
    }

    @FXML
    private void exportItems() throws IOException {
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("GROCERY LIST");

        Row row = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();

        CellStyle dataStyle = workbook.createCellStyle();

        DataFormat format = workbook.createDataFormat();

        CellStyle decimalStyle = workbook.createCellStyle();
        decimalStyle.setDataFormat(format.getFormat("#,##0.00"));

        CellStyle lockedDecimalStyle = workbook.createCellStyle();
        lockedDecimalStyle.setDataFormat(format.getFormat("#,##0.00"));


        CellStyle grandTotalStyle = workbook.createCellStyle();
        grandTotalStyle.setDataFormat(format.getFormat("#,##0.00"));


        HSSFFont headerFont = ((HSSFWorkbook) workbook).createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBold(true);
        headerFont.setColor(Font.COLOR_RED);
        headerStyle.setFont(headerFont);
        grandTotalStyle.setFont(headerFont);

        HSSFFont dataFont = ((HSSFWorkbook) workbook).createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 12);
        dataFont.setBold(false);
        dataStyle.setFont(dataFont);


        Cell itemCodeCell = row.createCell(0);
        itemCodeCell.setCellValue("ITEM CODE");
        itemCodeCell.setCellStyle(headerStyle);

        Cell itemNameCell = row.createCell(1);
        itemNameCell.setCellValue("ITEM NAME");
        itemNameCell.setCellStyle(headerStyle);

        Cell inStock = row.createCell(2);
        inStock.setCellValue("IN STOCK");
        inStock.setCellStyle(headerStyle);

        Cell originalPrice = row.createCell(3);
        originalPrice.setCellValue("ORIGINAL PRICE");
        originalPrice.setCellStyle(headerStyle);

        Cell averageCost = row.createCell(4);
        averageCost.setCellValue("AVERAGE COST");
        averageCost.setCellStyle(headerStyle);

        Cell total = row.createCell(5);
        total.setCellValue("TOTAL");
        total.setCellStyle(headerStyle);

        int groceryListSize = itemList.size();
        int rowNumber = 0;
        int totalStocks = 0;
        double totalInventoryCost = 0;

        for (; rowNumber < groceryListSize; rowNumber++) {
            Row data = sheet.createRow(rowNumber + 1);

            data.createCell(0);
            data.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(0).setCellValue(itemList.get(rowNumber).getItemCode());
            data.getCell(0).setCellStyle(dataStyle);

            data.createCell(1);
            data.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(1).setCellValue(itemList.get(rowNumber).getItemName());
            data.getCell(1).setCellStyle(dataStyle);

            data.createCell(2);
            data.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(2).setCellValue(itemList.get(rowNumber).getInStock());
            data.getCell(2).setCellStyle(dataStyle);

            data.createCell(3);
            data.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(3).setCellValue(itemList.get(rowNumber).getOriginalPrice());
            data.getCell(3).setCellStyle(decimalStyle);

            data.createCell(4);
            data.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(4).setCellValue(itemList.get(rowNumber).getAverageCost());
            data.getCell(4).setCellStyle(decimalStyle);

            data.createCell(5);
            data.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);
            data.getCell(5).setCellFormula("C"+ (rowNumber+2) +"*E"+(rowNumber+2));
            data.getCell(5).setCellValue(itemList.get(rowNumber).getAverageCost() * itemList.get(rowNumber).getInStock());
            data.getCell(5).setCellStyle(lockedDecimalStyle);

            totalStocks += itemList.get(rowNumber).getInStock();
            totalInventoryCost += itemList.get(rowNumber).getAverageCost() * itemList.get(rowNumber).getInStock();
        }

        Row footer = sheet.createRow(rowNumber + 1);
        footer.createCell(0);
        footer.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(0).setCellValue("GRAND TOTAL");
        footer.getCell(0).setCellStyle(headerStyle);

        footer.createCell(1);
        footer.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(1).setCellValue("");
        footer.getCell(1).setCellStyle(headerStyle);

        footer.createCell(2);
        footer.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(2).setCellValue(totalStocks);
        footer.getCell(2).setCellStyle(headerStyle);

        footer.createCell(3);
        footer.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(3).setCellValue("");
        footer.getCell(3).setCellStyle(headerStyle);

        footer.createCell(4);
        footer.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(4).setCellValue("PHP");
        footer.getCell(4).setCellStyle(headerStyle);

        footer.createCell(5);
        footer.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);
        footer.getCell(5).setCellFormula("SUM(F2:F"+(rowNumber+1)+")");
        footer.getCell(5).setCellValue(totalInventoryCost);
        footer.getCell(5).setCellStyle(grandTotalStyle);



        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd -hh-mm-ss");
        String strDate = dateFormat.format(date);
        Properties properties = new Properties();
        properties.load(InventoryService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        try (OutputStream fileOut = new FileOutputStream(System.getProperty("user.home") + "/Desktop/" + strDate.replaceAll("\\s+", "") + ".xls")) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void searchItemByName() {
        setTableItem();
    }


}
