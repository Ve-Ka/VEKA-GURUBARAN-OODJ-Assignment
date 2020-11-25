package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Delivery implements Task{
    private String deliveryID;
    private String deliveryDateTime;
    private String deliveryStaffID;
    private String custID;


    private final static String deliveryFile = "delivery.txt";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Aggregration
    private Item item;

    public Delivery(){}

    public Delivery(String deliveryID){
        this.deliveryID = deliveryID;
    }

    public Delivery(String deliveryDateTime, String deliveryStaffID, String custID, Item item){
        this.deliveryDateTime = deliveryDateTime;
        this.deliveryStaffID = deliveryStaffID;
        this.custID = custID;
        this.item = item;
    }

    @Override
    public void search() {

    }

    @Override
    public void add() {
        List<Delivery> defaultList = getAllDelivery();
        String newDeliveryID = String.format("DE%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                        - 1).getDeliveryID().replaceAll("DE", ""))) + 1));

        try{
            // Write to Delivery File
            FileWriter WriteData = new FileWriter(deliveryFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%s\n", newDeliveryID, deliveryDateTime, deliveryStaffID,
                    custID, item.getItemID()));
            WriteData.close();

            System.out.println("Alert: New Delivery Created!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void modify() {

    }

    @Override
    public void remove() {

    }

    protected List<Delivery> getAllDelivery(){
        List<Delivery> deliveryList = new ArrayList();
        try {
            List<String> deliveriesList = Files.readAllLines(Paths.get(deliveryFile));
            for (String record : deliveriesList){
                String[] rec = record.split("\\|");
                    Delivery delivery = new Delivery();
                    delivery.setDeliveryID(rec[0]);
                    delivery.setDeliveryDateTime(rec[1]);
                    delivery.setDeliveryStaffID(rec[2]);
                    delivery.setCustID(rec[3]);
                    item = new Item(rec[4]);
                    delivery.setItem(item);
                    deliveryList.add(delivery);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return deliveryList;
    }


    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getDeliveryStaffID() {
        return deliveryStaffID;
    }

    public void setDeliveryStaffID(String deliveryStaffID) {
        this.deliveryStaffID = deliveryStaffID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
