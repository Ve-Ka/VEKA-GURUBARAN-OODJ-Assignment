package Main;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Item implements MiscellaneousFunction{
    private String itemID;
    private String itemName;
    private int itemQuantity;
    private double itemPrice;
    private String itemSupplier;
    private String itemDescription;

    protected final static String itemDetailsFile = "txt Files/itemDetails.txt";

    public Item(){}

    public Item(String itemID, int itemQuantity){
        this.itemID = itemID;
        this.itemQuantity = itemQuantity;
    }

    public Item(String itemID, String itemName, int itemQuantity, double itemPrice, String itemSupplier,
                String itemDescription) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemSupplier = itemSupplier;
        this.itemDescription = itemDescription;
    }

    @Override
    public List<String> defaultDetails(String ID){
        List<Item> originalDetails = getAllItemDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (Item detail : originalDetails) {
            if (detail.getItemID().equals(ID)) {
                defaultDetails.add(detail.getItemID());
                defaultDetails.add(detail.getItemName());
                defaultDetails.add(Integer.toString(detail.getItemQuantity()));
                defaultDetails.add(Double.toString(detail.getItemPrice()));
                defaultDetails.add(detail.getItemSupplier());
                defaultDetails.add(detail.getItemDescription());
            }
        }
        return defaultDetails;
    }

    @Override
    public String generateID() {
        List<Item> defaultList = getAllItemDetails();
        String newItemID;
        try{
            newItemID = String.format("IT%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getItemID().replaceAll("IT", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newItemID = "IT0001";
        }
        return newItemID;
    }

    @Override
    public void generateReport() {
        try {
            FileReader in = new FileReader(itemDetailsFile);
            BufferedReader br = new BufferedReader(in);
            String record;
            ArrayList<Item> item = new ArrayList();
            while((record = br.readLine())!= null){
                String[] split = record.split("\\|");
                Item processed = new Item(split[0], split[1], Integer.parseInt(split[2]),
                        Double.parseDouble(split[3]), split[4], split[5]);
                item.add(processed);
            }
            br.close();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Generated Report/Item Report.pdf"));
            doc.open();
            doc.add(new Paragraph("Item Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD)));
            doc.add(new Paragraph(" "));

            // Defining Column Name
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Supplier");
            table.addCell("Description");

            for(Item object : item){
                table.addCell(object.getItemID());
                table.addCell(object.getItemName());
                table.addCell(Integer.toString(object.getItemQuantity()));
                table.addCell(Double.toString(object.getItemPrice()));
                table.addCell(object.getItemSupplier());
                table.addCell(object.getItemDescription());
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Feedback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    protected List<Item> getAllItemDetails(){
        List<Item> itemList = new ArrayList();
        try {
            List<String> itemDetailsList = Files.readAllLines(Paths.get(itemDetailsFile));
            for (String record : itemDetailsList){
                String[] rec = record.split("\\|");
                Item item = new Item();
                item.setItemID(rec[0]);
                item.setItemName(rec[1]);
                item.setItemQuantity(Integer.parseInt(rec[2]));
                item.setItemPrice(Double.parseDouble(rec[3]));
                item.setItemSupplier(rec[4]);
                item.setItemDescription(rec[5]);
                itemList.add(item);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return itemList;
    }

    protected void viewItemDetails(String itemID){
        List<Item> items = getAllItemDetails();
        for (Item item: items){
            if (item.itemID.equals(itemID)){
                System.out.println(item.toString());
            }
        }
    }

    protected void displayLimitedItemDetails(){
        List<Item> items = getAllItemDetails();
        System.out.println("\nAll item details");
        for (Item item: items){
            System.out.printf("%s|%s|%d|RM%.2f|%s|%s\n", item.getItemID(), item.getItemName(), item.getItemQuantity(),
                    item.getItemPrice(), item.getItemSupplier(), item.getItemDescription());
        }
        System.out.println("");
    }

    protected boolean modifyItemQuantity(String itemID, int quantitySold){
        List<Item> items = getAllItemDetails();
        for (Item item: items){
            if (item.itemID.equals(itemID)){
                if((item.getItemQuantity() < quantitySold) || (item.getItemQuantity() == 0)){
                    System.out.println("Warning: Quantity requested exceed inventory quantity!");
                    return false;
                }else if(quantitySold == 0){
                    return true;
                }
                else{
                    item.setItemQuantity(item.getItemQuantity() - quantitySold);
                }
            }
        }

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(Item.itemDetailsFile);
            for (Item detail : items) {
                WriteData.write(String.format("%s|%s|%d|%.2f|%s|%s\n", detail.getItemID(), detail.getItemName(),
                        detail.getItemQuantity(), detail.getItemPrice(), detail.getItemSupplier(),
                        detail.getItemDescription()));
            }
            WriteData.close();
            System.out.println("Alert: Item Quantity Updated\n");
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    protected String getItemID() {
        return itemID;
    }

    protected void setItemID(String itemID) {
        this.itemID = itemID;
    }

    protected String getItemName() {
        return itemName;
    }

    protected void setItemName(String itemName) {
        this.itemName = itemName;
    }

    protected int getItemQuantity() {
        return itemQuantity;
    }

    protected void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    protected double getItemPrice() {
        return itemPrice;
    }

    protected void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    protected String getItemSupplier() {
        return itemSupplier;
    }

    protected void setItemSupplier(String itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

    protected String getItemDescription() {
        return itemDescription;
    }

    protected void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public String toString() {
        return  "Name: " + itemName + '\n' +
                "Quantity: " + itemQuantity + '\n' +
                "Price: RM" + itemPrice + '\n' +
                "Supplier: " + itemSupplier + '\n' +
                "Description: " + itemDescription;
    }
}
