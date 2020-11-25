package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Delivery implements Task{
    private String deliveryID;
    private LocalDateTime deliveryDateTime;
    private String orderID;
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

    public Delivery(String deliveryID, LocalDateTime deliveryDateTime, String orderID, String deliveryStaffID,
                    String custID, Item item){
        this.deliveryID = deliveryID;
        this.deliveryDateTime = deliveryDateTime;
        this.orderID = orderID;
        this.deliveryStaffID = deliveryStaffID;
        this.custID = custID;
        this.item = item;
    }

    @Override
    public void search() {

    }

    @Override
    public void add() {

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
                    delivery.setDeliveryDateTime(LocalDateTime.parse(rec[1], dateTimeFormatter));
                    delivery.setOrderID(rec[2]);
                    delivery.setDeliveryStaffID(rec[3]);
                    delivery.setCustID(rec[4]);
                    item = new Item(rec[5]);
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

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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
