package com.monterdev.monterdepos.service;

import com.monterdev.monterdepos.components.ItemComponents;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.util.Prompt;
import javafx.fxml.FXML;

public class DeleteItemService extends ItemComponents {

    private ItemDao itemDao;

    @FXML
    private void deleteItem(){
        itemDao = ItemDao.getInstance();
        String itemCode = super.itemCode.getText();
        itemDao.deleteItemByItemCode(itemCode);
        Prompt.success(itemCode+" was successfully deleted!");
        clearFields();
        super.itemCode.setText("");
        super.itemCode.setDisable(false);
        super.btnUpdateItem.setVisible(false);
        super.btnCancel.setVisible(false);
        super.btnDeleteItem.setVisible(false);
        super.itemCode.requestFocus();

    }
}
