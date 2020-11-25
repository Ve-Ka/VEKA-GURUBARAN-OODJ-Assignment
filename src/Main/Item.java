package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private String itemID;
    private String itemName;
    private int itemQuantity;
    private double itemPrice;
    private String itemSupplier;
    private String itemDescription;

    protected final static String itemDetailsFile = "itemDetails.txt";

    public Item(){}

    public Item(String itemID){
        this.itemID = itemID;
    }

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

    protected List<String> defaultItemDetails(String itemID){
        List<Item> originalDetails = getAllItemDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (Item detail : originalDetails) {
            if (detail.getItemID().equals(itemID)) {
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
