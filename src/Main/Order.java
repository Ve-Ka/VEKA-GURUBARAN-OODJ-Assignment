package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order implements Task{
    private String orderID;
    private String orderDateTime;
    private String deliveryID;
    private String managingStaffID;
    private boolean orderCompletion;

    private final static String orderFile = "order.txt";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Aggregation
    private Customer customer;
    private Item item;


    public Order(){}

    public Order(String orderDateTime, Customer customer, String deliveryID, Item item, String managingStaffID,
                 boolean orderCompletion) {
        this.orderDateTime = orderDateTime;
        this.customer = customer;
        this.deliveryID = deliveryID;
        this.item = item;
        this.managingStaffID = managingStaffID;
        this.orderCompletion = orderCompletion;
    }

    // search can be depreciated
    @Override
    public void search() {

    }

    @Override
    public void add() {
        List<Order> defaultList = getAllOrder();
        String newOrderID = String.format("OD%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                - 1).getDeliveryID().replaceAll("OD", ""))) + 1));

        try{
            // Write to Delivery File
            FileWriter WriteData = new FileWriter(orderFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%s\n", newOrderID, orderDateTime, customer.getCustID(),
                    item.getItemID(), deliveryID, managingStaffID, orderCompletion));
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

    //customer -> order -> order list
    //object -> object item -> object list
    protected List<Order> getAllOrder(){
        List<Order> orderList = new ArrayList();
        try {
            List<String> ordersList = Files.readAllLines(Paths.get(orderFile));
            for (String record : ordersList){
                String[] rec = record.split("\\|");
                Order order = new Order();
                order.setOrderID(rec[0]);
                order.setOrderDateTime(rec[1]);
                customer = new Customer(rec[2]);
                order.setCustomer(customer);
                item = new Item(rec[3]);
                order.setItem(item);
                order.setDeliveryID(rec[4]);
                order.setManagingStaffID(rec[5]);
                order.setOrderCompletion(Boolean.parseBoolean(rec[6]));
                orderList.add(order);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return orderList;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getManagingStaffID() {
        return managingStaffID;
    }

    public void setManagingStaffID(String managingStaffID) {
        this.managingStaffID = managingStaffID;
    }

    public boolean isOrderCompletion() {
        return orderCompletion;
    }

    public void setOrderCompletion(boolean orderCompletion) {
        this.orderCompletion = orderCompletion;
    }

    public static String getOrderFile() {
        return orderFile;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", deliveryID='" + deliveryID + '\'' +
                ", managingStaffID='" + managingStaffID + '\'' +
                ", orderCompletion=" + orderCompletion +
                ", customer=" + customer +
                ", item=" + item +
                '}';
    }
}
