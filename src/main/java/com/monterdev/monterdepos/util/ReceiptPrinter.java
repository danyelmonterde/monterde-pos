package com.monterdev.monterdepos.util;

import com.monterdev.monterdepos.components.DashboardComponents;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.exception.POSException;
import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import static com.monterdev.monterdepos.constants.POSSymbols.*;
import static com.monterdev.monterdepos.constants.ReceiptLabels.*;
import static com.monterdev.monterdepos.constants.ReceiptLogs.ERROR_PRINTING_RECEIPT;
import static com.monterdev.monterdepos.constants.ReceiptLogs.NO_RECEIPT_PRINTED;

public class ReceiptPrinter extends DashboardComponents implements Printable {

    private static ReceiptPrinter receiptPrinter;

    private static final Logger LOGGER = LogManager.getLogger(CartService.class);

    private static final String RECEIPT_FONT ="AGENCY FB";

    private ItemDao itemDao;

    private ReceiptPrinter (){

    }

    public static ReceiptPrinter getInstance(){
        if(receiptPrinter == null){
            receiptPrinter = new ReceiptPrinter();
        }
        return receiptPrinter;
    }

    public void printReceipt(){
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                LOGGER.error(new POSException(ERROR_PRINTING_RECEIPT, ex.getCause()));
            }
        }else{
            Prompt.success(NO_RECEIPT_PRINTED);
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {
        itemDao = ItemDao.getInstance();
        int r = cart.getItems().size();
        // ImageIcon icon=new ImageIcon("C:UsersccsDocumentsNetBeansProjectsvideo TestPOSInvoicesrcposinvoicemylogo.jpg");
        int result = NO_SUCH_PAGE;
        if (page == 0) {

            Graphics2D g2d = (Graphics2D) g;
            double width = pf.getImageableWidth();
            g2d.translate((int) pf.getImageableX(), (int) pf.getImageableY());

            try {
                int y = 30;
                int yShift = 15;
                int headerRectHeight = 40;
                int x=5;



                g2d.setFont(new Font(RECEIPT_FONT, Font.TRUETYPE_FONT, 14));
                //g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);y+=yShift+30;
                g2d.drawString(BROKEN_LINES, x, y);
                y += yShift;
                g2d.drawString(COMPANY_NAME, x, y);
                y += yShift;
                g2d.drawString(COMPANY_ADDRESS_LINE_1, x, y);
                y += yShift;
                g2d.drawString(COMPANY_ADDRESS_LINE_2, x, y);
                y += yShift;
                g2d.drawString(COMPANY_WEBSITE, x, y);
                y += yShift;
                g2d.drawString(COMPANY_CONTACT_NUMBER, x, y);
                y += yShift;
                g2d.drawString(BROKEN_LINES, x, y);
                y += headerRectHeight;

                g2d.drawString(RECEIPT_HEADERS, x, y);
                y += yShift;
                g2d.drawString(BROKEN_LINES, x, y);
                y += headerRectHeight;


                for (int s = 0; s < r; s++) {
                    itemDao = ItemDao.getInstance();

                    Item item = itemDao.getItemByItemCode(cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[0]);
                    double sumOfItemsInCart = item.getAverageCost() * Integer.parseInt(cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[2]);
                    g2d.drawString(" " + cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[1].toUpperCase() + "                            ", x, y);
                    y += yShift;
                    g2d.drawString("      " + cart.getItems().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[2].toUpperCase() + " X " + item.getAverageCost(), x, y);
                    g2d.drawString(String.valueOf(sumOfItemsInCart), 160, y);
                    y += yShift;
                }
                g2d.drawString(BROKEN_LINES, x, y);
                y += yShift;
                g2d.drawString(TOTAL_AMOUNT + grandTotal.getText() + "   ", x, y);
                y += yShift;
                g2d.drawString(BROKEN_LINES, x, y);
                y += yShift;
                g2d.drawString(CASH + amountPaid.getText() + "   ", x, y);
                y += yShift;
                g2d.drawString(BROKEN_LINES, x, y);
                y += yShift;
                g2d.drawString( BALANCE+ change.getText() + "   ", x, y);
                y += yShift;

                g2d.drawString(STARS_ROW_SEPARATOR, x, y);
                y += headerRectHeight;
                g2d.drawString(THANK_YOU, x, y);
                y += yShift;
                g2d.drawString(STARS_ROW_SEPARATOR, x, y);
                y += headerRectHeight;
                g2d.drawString(SOFTWARE_BY, x, y);
                y += yShift;
                g2d.drawString(SOFTWARE_CONTACT_NUMBER, x, y);
                y += yShift;


            } catch (Exception e) {
                e.printStackTrace();
            }

            result = PAGE_EXISTS;
        }
        return result;
    }
}
