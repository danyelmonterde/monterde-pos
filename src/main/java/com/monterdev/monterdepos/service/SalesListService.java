package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.SalesListComponents;
import com.monterdev.monterdepos.dao.SalesDao;
import com.monterdev.monterdepos.model.Sales;
import com.monterdev.monterdepos.util.Prompt;
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
import java.time.*;
import java.util.*;

import static com.monterdev.monterdepos.constants.ConfigFile.CONFIG_FILE_NAME;

public class SalesListService extends SalesListComponents implements Initializable {

    private int BEGIN_INDEX = 0; //Initial number of rows per page
    private int END_INDEX = 10; //Initial number of rows per page

    private List<Sales> salesList;

    private ObservableList<Sales> salesObservableList;

    private SalesDao salesDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        salesDao = SalesDao.getInstance();
        dateFrom.setValue(LocalDate.now());
        dateUntil.setValue(LocalDate.now());

        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnTransactionNumber.setCellValueFactory(new PropertyValueFactory<>("transactionNumber"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateSold.setCellValueFactory(new PropertyValueFactory<>("dateTransacted"));

        setTableSalesList();
    }

    @FXML
    private void setTableSalesList() {
        LocalDate dateFromValue = dateFrom.getValue();
        Instant instant1 = Instant.from(dateFromValue.atStartOfDay(ZoneId.systemDefault()));
        Date currentDateFrom = Date.from(instant1);

        LocalDate dateUntilValue = dateUntil.getValue();
        Instant instant2 = Instant.from(dateUntilValue.atStartOfDay(ZoneId.systemDefault()).plusHours(23).plusMinutes(59));
        Date currentDateUntil = Date.from(instant2);

        salesList = salesDao.getSalesByTransactionNumberCategoryDateAndPaging(txtSearchTransaction.getText(), currentDateFrom, currentDateUntil, BEGIN_INDEX, END_INDEX);
        salesObservableList = FXCollections.observableArrayList();
        salesList.stream().forEach(data -> {
            salesObservableList.add(data);
        });

        super.tblSalesList.setItems(salesObservableList);
    }

    @FXML
    private void searchByTransactionNumber() {
        setTableSalesList();
    }

    @FXML
    private void exportItems() throws IOException {
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("SALES LIST");

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
        decimalStyle.setFont(dataFont);
        lockedDecimalStyle.setFont(dataFont);


        Cell itemCodeCell = row.createCell(0);
        itemCodeCell.setCellValue("TRANSACTION NUMBER");
        itemCodeCell.setCellStyle(headerStyle);

        Cell itemNameCell = row.createCell(1);
        itemNameCell.setCellValue("ITEM NAME");
        itemNameCell.setCellStyle(headerStyle);

        Cell inStock = row.createCell(2);
        inStock.setCellValue("PRICE");
        inStock.setCellStyle(headerStyle);

        Cell originalPrice = row.createCell(3);
        originalPrice.setCellValue("QUANTITY");
        originalPrice.setCellStyle(headerStyle);

        Cell averageCost = row.createCell(4);
        averageCost.setCellValue("TOTAL");
        averageCost.setCellStyle(headerStyle);

        Cell dateSold = row.createCell(5);
        dateSold.setCellValue("DATE SOLD");
        dateSold.setCellStyle(headerStyle);

        int salesListSize = salesList.size();
        int rowNumber = 0;
        int totalStocks = 0;
        double totalInventoryCost = 0;

        for (; rowNumber < salesListSize; rowNumber++) {
            Row data = sheet.createRow(rowNumber + 1);

            data.createCell(0);
            data.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(0).setCellValue(salesList.get(rowNumber).getTransactionNumber());
            data.getCell(0).setCellStyle(dataStyle);

            data.createCell(1);
            data.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(1).setCellValue(salesList.get(rowNumber).getItemName());
            data.getCell(1).setCellStyle(dataStyle);

            data.createCell(2);
            data.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(2).setCellValue(salesList.get(rowNumber).getPrice());
            data.getCell(2).setCellStyle(decimalStyle);

            data.createCell(3);
            data.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            data.getCell(3).setCellValue(salesList.get(rowNumber).getQuantity());
            data.getCell(3).setCellStyle(dataStyle);

            data.createCell(4);
            data.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
            data.getCell(4).setCellFormula("C" + (rowNumber + 2) + "*D" + (rowNumber + 2));
            data.getCell(4).setCellValue(salesList.get(rowNumber).getPrice() * salesList.get(rowNumber).getQuantity());
            data.getCell(4).setCellStyle(lockedDecimalStyle);

            data.createCell(5);
            data.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            DateFormat dateBoughtFromSupplier = new SimpleDateFormat("YYYY-MM-dd");
            String strDateBoughtFromSupplier = dateBoughtFromSupplier.format(salesList.get(rowNumber).getDateTransacted());
            data.getCell(5).setCellValue(strDateBoughtFromSupplier);
            data.getCell(5).setCellStyle(dataStyle);


            totalStocks += salesList.get(rowNumber).getQuantity();
            totalInventoryCost += salesList.get(rowNumber).getPrice() * salesList.get(rowNumber).getQuantity();
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
        footer.getCell(2).setCellValue("");
        footer.getCell(2).setCellStyle(headerStyle);

        footer.createCell(3);
        footer.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(3).setCellValue(totalStocks);
        footer.getCell(3).setCellStyle(headerStyle);

        footer.createCell(4);
        footer.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
        footer.getCell(4).setCellFormula("SUM(E2:E" + (rowNumber + 1) + ")");
        footer.getCell(4).setCellValue(totalInventoryCost);
        footer.getCell(4).setCellStyle(grandTotalStyle);

        footer.createCell(5);
        footer.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
        footer.getCell(5).setCellValue("");
        footer.getCell(5).setCellStyle(headerStyle);


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
        try (OutputStream fileOut = new FileOutputStream(System.getProperty("user.home") + "/OneDrive/Desktop/"+ strDate.replaceAll("\\s+", "") + "-sales" + ".xls")) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            Prompt.success("Data was saved to Desktop!");
        }
    }

    @FXML
    private void tenRows() {
        END_INDEX = 10;
        setTableSalesList();
    }

    @FXML
    private void fiftyRows() {
        END_INDEX = 50;
        setTableSalesList();
    }

    @FXML
    private void hundredRows() {
        END_INDEX = 100;
        setTableSalesList();
    }

    @FXML
    private void thousandRows() {
        END_INDEX = 10000;
        setTableSalesList();
    }

}
