package com.monterdev.monterdepos.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class InventoryListComponents {

    @FXML
    protected ComboBox comboStocks;

    @FXML
    protected ComboBox comboCategory;

    @FXML
    protected Button btnExport;

    @FXML
    protected Button btnTen;

    @FXML
    protected Button btnFifty;

    @FXML
    protected Button btnHundred;

    @FXML
    protected Button btnThousand;

    @FXML
    protected Button btnPrevious;

    @FXML
    protected Button btnNext;

    @FXML
    protected TableView tblItemList;
}
