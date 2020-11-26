package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Order implements Task{
    private String orderID;
    private String orderDateTime;
    private String deliveryID;
    private String managingStaffID;
    private boolean orderCompletion;

    private final static String orderFile = "order.txt";

    // Aggregation
    private Customer customer;
    private Item item;

    public Order(){}

    public Order(String orderID, String orderDateTime, Customer customer, Item item, String managingStaffID,
                 boolean orderCompletion) {
        this.orderID = orderID;
        this.orderDateTime = orderDateTime;
        this.customer = customer;
        this.deliveryID = "";
        this.item = item;
        this.managingStaffID = managingStaffID;
        this.orderCompletion = orderCompletion;
    }

    public Order(String orderID, String orderDateTime, Customer customer, String deliveryID, Item item,
                 String managingStaffID, boolean orderCompletion) {
        this.orderID = orderID;
        this.orderDateTime = orderDateTime;
        this.customer = customer;
        this.deliveryID = deliveryID;
        this.item = item;
        this.managingStaffID = managingStaffID;
        this.orderCompletion = orderCompletion;
    }

    // search can be depreciated
    @Override
    public void search(String ID) {
        List<Order> orderList = getAllOrder();
        for(Order item: orderList){
            if(item.getOrderID().equals(ID)){
                System.out.println(item.toString());
            }
        }
    }

    @Override
    public void add() {
        try{
            // Write to Delivery File
            FileWriter WriteData = new FileWriter(orderFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%d|%s|%s|%s\n", orderID, orderDateTime, customer.getCustID(),
                    item.getItemID(), item.getItemQuantity(), deliveryID, managingStaffID, orderCompletion));
            WriteData.close();

            System.out.println("Alert: New Order Created!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void modify(Object object) {

    }

    @Override
    public void remove(String ID) {
        // Remove Details from Customer Object List
        //Order order = new Order();
        List<Order> orderList = getAllOrder();
        for(Order item: orderList){
            System.out.println(item.getOrderID());
            if(item.getOrderID().equals(ID)){
                setDeliveryID(item.getDeliveryID());
            }
        }
        orderList.removeIf(order1 -> order1.getOrderID().equals(ID));

        // Write all data to file
        try {
            FileWriter WriteData = new FileWriter(orderFile);
            for (Order order2 : orderList) {
                WriteData.write(String.format("%s|%s|%s|%s|%d|%s|%s|%s\n", order2.getOrderID(),
                        order2.getOrderDateTime(), order2.getCustomer().getCustID(), order2.getItem().getItemID(),
                        order2.getItem().getItemQuantity(), order2.getDeliveryID(), order2.getManagingStaffID(),
                        order2.getOrderCompletion()));
            }
            WriteData.close();
            System.out.println("Alert: Order Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> defaultDetails(String ID){
        List<Order> originalDetails = getAllOrder();
        List<String> defaultDetails = new ArrayList<>();
        for (Order detail : originalDetails) {
            if (detail.getOrderID().equals(ID)) {
                defaultDetails.add(detail.getOrderID());
                defaultDetails.add(detail.getOrderDateTime());
                defaultDetails.add(detail.getCustomer().getCustID());
                defaultDetails.add(detail.getItem().getItemID());
                defaultDetails.add(Integer.toString(detail.getItem().getItemQuantity()));
                defaultDetails.add(detail.getDeliveryID());
                defaultDetails.add(detail.getManagingStaffID());
                defaultDetails.add(Boolean.toString(detail.getOrderCompletion()));
            }
        }
        return defaultDetails;
    }

    @Override
    public String generateID(){
        List<Order> defaultList = getAllOrder();
        String newOrderID;
        try{
            newOrderID = String.format("OD%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getOrderID().replaceAll("OD", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newOrderID = "OD0001";
        }
        return newOrderID;
    }

    //customer -> order -> order list
    //object -> object item -> object list
    // to be added into interface after renaming
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
                item = new Item(rec[3], Integer.parseInt(rec[4]));
                order.setItem(item);
                order.setDeliveryID(rec[5]);
                order.setManagingStaffID(rec[6]);
                order.setOrderCompletion(Boolean.parseBoolean(rec[7]));
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

    public boolean getOrderCompletion() {
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
        return  "Order Date Time: " + orderDateTime + '\n' +
                "Customer ID: " + customer.getCustID() + '\n' +
                "Item ID: " + item.getItemID() + '\n' +
                "Item Quantity: " + item.getItemQuantity() + '\n' +
                "Delivery ID: " + deliveryID + '\n' +
                "Managing Staff ID: " + managingStaffID + '\n' +
                "Order Completed: " + orderCompletion;
    }
}
