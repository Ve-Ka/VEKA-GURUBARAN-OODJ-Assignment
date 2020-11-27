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

public class Delivery implements Task, MiscellaneousFunction{
    private String deliveryID;
    private String deliveryDateTime;
    private String deliveryStaffID;
    private String custID;
    private String custName;
    private String custAddress;

    private final static String deliveryFile = "txt Files/delivery.txt";

    // Aggregation
    private Item item;

    public Delivery(){}

    public Delivery(String deliveryID, String deliveryDateTime, String deliveryStaffID, String custID, Item item){
        this.deliveryID = deliveryID;
        this.deliveryDateTime = deliveryDateTime;
        this.deliveryStaffID = deliveryStaffID;
        this.custID = custID;
        this.item = item;
    }

    public Delivery(String deliveryID, String deliveryDateTime, String deliveryStaffID, String custID,
                    String custName, String custAddress, String itemID, int itemQuantity){
        this.deliveryID = deliveryID;
        this.deliveryDateTime = deliveryDateTime;
        this.deliveryStaffID = deliveryStaffID;
        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        item = new Item(itemID, itemQuantity);
    }

    @Override
    public void search(String ID) {
        List<Delivery> deliveryList = getAllDelivery();
        for(Delivery item: deliveryList){
            if(item.getDeliveryID().equals(ID)){
                System.out.println(item.toString());
            }
        }
    }

    @Override
    public void add() {
        Customer customer = new Customer();
        List<Customer> customerInfo = customer.getAllCustDetails();
        for (Customer customers : customerInfo){
            if (customers.getCustID().equals(custID)){
                setCustName(customers.getCustName());
                setCustAddress(customers.getCustAddress());
            }
        }

        try{
            // Write to Delivery File
            FileWriter WriteData = new FileWriter(deliveryFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%s|%s|%s|%d\n", deliveryID, deliveryDateTime, deliveryStaffID,
                    custID, custName, custAddress, item.getItemID(), item.getItemQuantity()));
            WriteData.close();

            System.out.println("Alert: New Delivery Created!\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void modify(Object object) {
        // Overwrite Original List with new data
        Delivery delivery = (Delivery) object;
        List<Delivery> originalDetails = getAllDelivery();
        int position = 0;
        for (Delivery detail: originalDetails){
            if (detail.getDeliveryID().equals(delivery.getDeliveryID())) {
                break;
            }else{
                position ++;
            }
        }

        originalDetails.set(position, delivery);

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(deliveryFile);
            for (Delivery detail: originalDetails){
                WriteData.write(String.format("%s|%s|%s|%s|%s|%s|%s|%d\n", detail.getDeliveryID(),
                        detail.getDeliveryDateTime(), detail.getDeliveryStaffID(), detail.getCustID(),
                        detail.getCustName(), detail.getCustAddress(), detail.getItem().getItemID(),
                        detail.getItem().getItemQuantity()));
            }
            WriteData.close();
            System.out.println("Alert: Delivery Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String ID) {
        // Remove Details from Customer Object List
        //Delivery delivery = new Delivery();
        List<Delivery> deliveryList = getAllDelivery();
        deliveryList.removeIf(delivery1 -> delivery1.getDeliveryID().equals(ID));

        // Write all data to file
        try {
            FileWriter WriteData = new FileWriter(deliveryFile);
            for (Delivery delivery2 : deliveryList) {
                WriteData.write(String.format("%s|%s|%s|%s|%s|%s|%s|%d\n", delivery2.getDeliveryID(),
                        delivery2.getDeliveryDateTime(), delivery2.getDeliveryStaffID(), delivery2.getCustID(),
                        delivery2.getCustName(), delivery2.getCustAddress(), delivery2.getItem().getItemID(),
                        delivery2.getItem().getItemQuantity()));
            }
            WriteData.close();
            System.out.println("Alert: Delivery Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> defaultDetails(String ID){
        List<Delivery> originalDetails = getAllDelivery();
        List<String> defaultDetails = new ArrayList<>();
        for (Delivery detail : originalDetails) {
            if (detail.getDeliveryID().equals(ID)) {
                defaultDetails.add(detail.getDeliveryID());
                defaultDetails.add(detail.getDeliveryDateTime());
                defaultDetails.add(detail.getDeliveryStaffID());
                defaultDetails.add(detail.getCustID());
                defaultDetails.add(detail.getCustName());
                defaultDetails.add(detail.getCustAddress());
                defaultDetails.add(detail.getItem().getItemID());
                defaultDetails.add(Integer.toString(detail.getItem().getItemQuantity()));
            }
        }
        return defaultDetails;
    }

    @Override
    public String generateID(){
        List<Delivery> defaultList = getAllDelivery();
        String newDeliveryID;
        try{
            newDeliveryID = String.format("DE%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getDeliveryID().replaceAll("DE", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newDeliveryID = "DE0001";
        }
        return newDeliveryID;
    }

    @Override
    public void generateReport(){
        try {
            FileReader in = new FileReader(deliveryFile);
            BufferedReader br = new BufferedReader(in);
            String record;
            ArrayList<Delivery> item = new ArrayList();
            while((record = br.readLine())!= null){
                String[] split = record.split("\\|");
                Delivery processed = new Delivery(split[0], split[1], split[2], split[3], split[4], split[5],
                        split[6], Integer.parseInt(split[7]));
                item.add(processed);
            }
            br.close();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Generated Report/Delivery Report.pdf"));
            doc.open();
            doc.add(new Paragraph("Delivery Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD)));
            doc.add(new Paragraph(" "));

            // Defining Column Name
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.addCell("Delivery ID");
            table.addCell("Date Time Created");
            table.addCell("Delivery Staff ID");
            table.addCell("Customer ID");
            table.addCell("Customer Name");
            table.addCell("Customer Address");
            table.addCell("Item ID");
            table.addCell("Item Quantity");

            for(Delivery object : item){
                table.addCell(object.getDeliveryID());
                table.addCell(object.getDeliveryDateTime());
                table.addCell(object.getDeliveryStaffID());
                table.addCell(object.getCustID());
                table.addCell(object.getCustName());
                table.addCell(object.getCustAddress());
                table.addCell(object.getItem().getItemID());
                table.addCell(Integer.toString(object.getItem().getItemQuantity()));
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Feedback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
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
                    delivery.setCustName(rec[4]);
                    delivery.setCustAddress(rec[5]);
                    item = new Item(rec[6], Integer.parseInt(rec[7]));
                    delivery.setItem(item);
                    deliveryList.add(delivery);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return deliveryList;
    }

    protected void viewSelfDelivery(String empID){
        Order order = new Order();
        List<Order> orderList = order.getAllOrder();
        List<Delivery> deliveryList = getAllDelivery();
        for(Delivery item: deliveryList){
            for (Order item1: orderList){
                if(item.getDeliveryID().equals(item1.getDeliveryID())){
                    if((item.getDeliveryStaffID().equals(empID)) && !(item1.getOrderCompletion())){
                        System.out.println("Delivery ID: " + item.getDeliveryID());
                        System.out.println(item.toString());
                        System.out.println("+~~~~~~~~~~~~~~~~~~~~~~~+");
                    }
                }
            }
        }
    }

    protected boolean selfDeliveryVerification(String empID){
        List<Delivery> deliveryList = getAllDelivery();
        for(Delivery item: deliveryList){
            if(item.getDeliveryStaffID().equals(empID)){
                return true;
            }
        }return false;
    }

    private String getDeliveryID() {
        return deliveryID;
    }

    private void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    private String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    private void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    protected String getDeliveryStaffID() {
        return deliveryStaffID;
    }

    private void setDeliveryStaffID(String deliveryStaffID) {
        this.deliveryStaffID = deliveryStaffID;
    }

    private String getCustID() {
        return custID;
    }

    private void setCustID(String custID) {
        this.custID = custID;
    }

    private String getCustName() {
        return custName;
    }

    private void setCustName(String custName) {
        this.custName = custName;
    }

    private String getCustAddress() {
        return custAddress;
    }

    private void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    private Item getItem() {
        return item;
    }

    private void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return  "Created Date Time: " + deliveryDateTime + '\n' +
                "Delivery Staff ID: " + deliveryStaffID + '\n' +
                "Customer ID: " + custID + '\n' +
                "Customer Name: " + custName + '\n' +
                "Customer Address: " + custAddress + '\n' +
                "Item ID: " + item.getItemID() + '\n' +
                "Item Quantity: " + item.getItemQuantity();
    }
}
