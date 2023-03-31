package com.monterdev.monterdepos.util;

import com.monterdev.monterdepos.components.DashboardComponents;
import com.monterdev.monterdepos.dao.ItemDao;
import com.monterdev.monterdepos.service.CartService;
import javafx.print.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.IOException;
import java.io.InputStream;

public class ReceiptPrinter extends DashboardComponents {

    private static ReceiptPrinter receiptPrinter;

    private static final Logger LOGGER = LogManager.getLogger(CartService.class);

    private static final String RECEIPT_FONT = "AGENCY FB";

    private ItemDao itemDao;

    private ReceiptPrinter() {

    }

    public static ReceiptPrinter getInstance() {
        if (receiptPrinter == null) {
            receiptPrinter = new ReceiptPrinter();
        }
        return receiptPrinter;
    }

    public void printReceipt() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        Stage stage = new Stage();
        if (true) {
            String selectedPrinter = job.getPrinter().getName();

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));
            PrintService pss[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.GIF, pras);
            if (pss.length == 0) {
                throw new RuntimeException("No printer services available.");
            }
            int i = 0;
            for (i = 0; i < pss.length; i++) {
                if (pss[i].getName().equals(selectedPrinter)) {
                    break;
                }
            }
            PrintService ps = pss[i];
            System.out.println("Printing to " + ps);
            DocPrintJob docPrintJob = ps.createPrintJob();

//            InputStream fin = ReceiptPrinter.class.getClassLoader().getResourceAsStream("passport-daniel.jpg");
            InputStream fin = null;
            // fin = new FileInputStream();
            fin = ReceiptPrinter.class.getClassLoader().getResourceAsStream("passport-daniel.jpg");
            Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.GIF, null);
            try {
                docPrintJob.print(doc, pras);
            } catch (PrintException ex) {
                System.out.println(ex);
            }
            try {
                fin.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

//    @Override
//    public int print(Graphics g, PageFormat pf, int page) throws
//            PrinterException {
//        itemDao = ItemDao.getInstance();
//        int r = cart.getItemsByItemCode().size();
//        // ImageIcon icon=new ImageIcon("C:UsersccsDocumentsNetBeansProjectsvideo TestPOSInvoicesrcposinvoicemylogo.jpg");
//        int result = NO_SUCH_PAGE;
//        if (page == 0) {
//
//            Graphics2D g2d = (Graphics2D) g;
//            double width = pf.getImageableWidth();
//            g2d.translate((int) pf.getImageableX(), (int) pf.getImageableY());
//
//            try {
//                int y = 30;
//                int yShift = 15;
//                int headerRectHeight = 40;
//                int x=5;
//
//
//
//                g2d.setFont(new Font(RECEIPT_FONT, Font.TRUETYPE_FONT, 14));
//                //g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);y+=yShift+30;
//                g2d.drawString(BROKEN_LINES, x, y);
//                y += yShift;
//                g2d.drawString(COMPANY_NAME, x, y);
//                y += yShift;
//                g2d.drawString(COMPANY_ADDRESS_LINE_1, x, y);
//                y += yShift;
//                g2d.drawString(COMPANY_ADDRESS_LINE_2, x, y);
//                y += yShift;
//                g2d.drawString(COMPANY_WEBSITE, x, y);
//                y += yShift;
//                g2d.drawString(COMPANY_CONTACT_NUMBER, x, y);
//                y += yShift;
//                g2d.drawString(BROKEN_LINES, x, y);
//                y += headerRectHeight;
//
//                g2d.drawString(RECEIPT_HEADERS, x, y);
//                y += yShift;
//                g2d.drawString(BROKEN_LINES, x, y);
//                y += headerRectHeight;
//
//
//                for (int s = 0; s < r; s++) {
//                    itemDao = ItemDao.getInstance();
//
//                    Item item = itemDao.getItemByItemCode(cart.getItemsByItemCode().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[0]);
//                    double sumOfItemsInCart = item.getAverageCost() * Integer.parseInt(cart.getItemsByItemCode().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[2]);
//                    g2d.drawString(" " + cart.getItemsByItemCode().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[1].toUpperCase() + "                            ", x, y);
//                    y += yShift;
//                    g2d.drawString("      " + cart.getItemsByItemCode().get(s).toString().split(CART_TEXT_SEPARATOR_REGEX)[2].toUpperCase() + " X " + item.getAverageCost(), x, y);
//                    g2d.drawString(String.valueOf(sumOfItemsInCart), 160, y);
//                    y += yShift;
//                }
//                g2d.drawString(BROKEN_LINES, x, y);
//                y += yShift;
//                g2d.drawString(TOTAL_AMOUNT + grandTotal.getText() + "   ", x, y);
//                y += yShift;
//                g2d.drawString(BROKEN_LINES, x, y);
//                y += yShift;
//                g2d.drawString(CASH + amountPaid.getText() + "   ", x, y);
//                y += yShift;
//                g2d.drawString(BROKEN_LINES, x, y);
//                y += yShift;
//                g2d.drawString( BALANCE+ change.getText() + "   ", x, y);
//                y += yShift;
//
//                g2d.drawString(STARS_ROW_SEPARATOR, x, y);
//                y += headerRectHeight;
//                g2d.drawString(THANK_YOU, x, y);
//                y += yShift;
//                g2d.drawString(STARS_ROW_SEPARATOR, x, y);
//                y += headerRectHeight;
//                g2d.drawString(SOFTWARE_BY, x, y);
//                y += yShift;
//                g2d.drawString(SOFTWARE_CONTACT_NUMBER, x, y);
//                y += yShift;
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            result = PAGE_EXISTS;
//        }
//        return result;
//    }
}
