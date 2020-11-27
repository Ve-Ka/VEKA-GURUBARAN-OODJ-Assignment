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

public class Feedback implements Task, MiscellaneousFunction{
    private String feedbackID;
    private String feedbackDateTime;
    private String feedbackTitle;
    private String feedbackContent;
    private String managingStaffID;

    protected final static String feedbackFile = "txt Files/feedback.txt";

    private Customer customer;

    public Feedback(){}

    public Feedback(String feedbackID, String feedbackDateTime, String feedbackTitle, String feedbackContent,
                    Customer customer, String managingStaffID){
        this.feedbackID = feedbackID;
        this.feedbackDateTime = feedbackDateTime;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
        this.customer = customer;
        this.managingStaffID = managingStaffID;
    }

    public Feedback(String feedbackID, String feedbackDateTime, String feedbackTitle, String feedbackContent,
                    String customerID, String customerName, String customerPhoneNumber, String managingStaffID){
        this.feedbackID = feedbackID;
        this.feedbackDateTime = feedbackDateTime;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
        customer = new Customer(customerID, customerName, customerPhoneNumber);
        this.managingStaffID = managingStaffID;
    }

    @Override
    public void search(String ID) {
        List<Feedback> feedbackList = getAllFeedbackDetails();
        for(Feedback item: feedbackList){
            if(item.getFeedbackID().equals(ID)){
                System.out.println(item.toString());
            }
        }
    }

    @Override
    public void add() {
        List<Customer> customerInfo = customer.getAllCustDetails();
        for (Customer customers : customerInfo){
            if (customers.getCustID().equals(customer.getCustID())){
                customer.setCustName(customers.getCustName());
                customer.setCustPhoneNo(customers.getCustPhoneNo());
            }
        }

        if(customer.getCustName() == null){
            System.out.println("Warning: Customer does not exist!");
        }else{
            try{
                // Write to Delivery File
                FileWriter WriteData = new FileWriter(feedbackFile, true);
                WriteData.write(String.format("%s|%s|%s|%s|%s|%s|%s|%s\n", feedbackID, feedbackDateTime,
                        feedbackTitle, feedbackContent, customer.getCustID(), customer.getCustName(),
                        customer.getCustPhoneNo(), managingStaffID));
                WriteData.close();

                System.out.println("Alert: New Feedback Created!");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void modify(Object object) {
        // Overwrite Original List with new data
        Feedback feedback = (Feedback) object;
        List<Feedback> originalDetails = getAllFeedbackDetails();
        int position = 0;
        for (Feedback detail: originalDetails){
            if (detail.getFeedbackID().equals(feedback.getFeedbackID())) {
                break;
            }else{
                position ++;
            }
        }

        originalDetails.set(position, feedback);

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(feedbackFile);
            for (Feedback detail: originalDetails){
                WriteData.write(String.format("%s|%s|%s|%s|%s|%s|%s|%s\n", detail.getFeedbackID(),
                        detail.getFeedbackDateTime(), detail.getFeedbackTitle(), detail.getFeedbackContent(),
                        detail.getCustomer().getCustID(), detail.getCustomer().getCustName(),
                        detail.getCustomer().getCustPhoneNo(), detail.getManagingStaffID()));
            }
            WriteData.close();
            System.out.println("Alert: Feedback Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String ID) {
        // Remove Details from Customer Object List
        //Delivery delivery = new Delivery();
        List<Feedback> feedbackList = getAllFeedbackDetails();
        feedbackList.removeIf(feedback1 -> feedback1.getFeedbackID().equals(ID));

        // Write all data to file
        try {
            FileWriter WriteData = new FileWriter(feedbackFile);
            for (Feedback feedback2 : feedbackList) {
                WriteData.write(String.format("%s|%s|%s|%s|%s|%s|%s|%s\n", feedback2.getFeedbackID(),
                        feedback2.getFeedbackDateTime(), feedback2.getFeedbackTitle(), feedback2.getFeedbackContent(),
                        feedback2.getCustomer().getCustID(), feedback2.getCustomer().getCustName(),
                        feedback2.getCustomer().getCustPhoneNo(), feedback2.getManagingStaffID()));
            }
            WriteData.close();
            System.out.println("Alert: Feedback Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> defaultDetails(String ID) {
        List<Feedback> originalDetails = getAllFeedbackDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (Feedback detail : originalDetails) {
            if (detail.getFeedbackID().equals(ID)) {
                defaultDetails.add(detail.getFeedbackID());
                defaultDetails.add(detail.getFeedbackDateTime());
                defaultDetails.add(detail.getFeedbackTitle());
                defaultDetails.add(detail.getFeedbackContent());
                defaultDetails.add(detail.getCustomer().getCustID());
                defaultDetails.add(detail.getCustomer().getCustName());
                defaultDetails.add(detail.getCustomer().getCustPhoneNo());
                defaultDetails.add(detail.getManagingStaffID());
            }
        }
        return defaultDetails;
    }

    @Override
    public String generateID() {
        List<Feedback> defaultList = getAllFeedbackDetails();
        String newFeedbackID;
        try{
            newFeedbackID = String.format("FE%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getFeedbackID().replaceAll("FE", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newFeedbackID = "FE0001";
        }
        return newFeedbackID;
    }

    @Override
    public void generateReport(){
        try {
            FileReader in = new FileReader(feedbackFile);
            BufferedReader br = new BufferedReader(in);
            String record;
            ArrayList<Feedback> item = new ArrayList();
            while((record = br.readLine())!= null){
                String[] split = record.split("\\|");
                Feedback processed = new Feedback(split[0], split[1], split[2], split[3], split[4], split[5],
                        split[6], split[7]);
                item.add(processed);
            }
            br.close();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Generated Report/Feedback Report.pdf"));
            doc.open();
            doc.add(new Paragraph("Feedback Report",FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD)));
            doc.add(new Paragraph(" "));

            // Defining Column Name
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.addCell("Feedback ID");
            table.addCell("Date Time Created");
            table.addCell("Title");
            table.addCell("Content");
            table.addCell("Customer ID");
            table.addCell("Customer Name");
            table.addCell("Customer Phone Number");
            table.addCell("Managing Staff ID");

            for(Feedback object : item){
                table.addCell(object.getFeedbackID());
                table.addCell(object.getFeedbackDateTime());
                table.addCell(object.getFeedbackTitle());
                table.addCell(object.getFeedbackContent());
                table.addCell(object.getCustomer().getCustID());
                table.addCell(object.getCustomer().getCustName());
                table.addCell(object.getCustomer().getCustPhoneNo());
                table.addCell(object.getManagingStaffID());
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Feedback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    protected List<Feedback> getAllFeedbackDetails(){
        List<Feedback> feedbackList = new ArrayList();
        try {
            List<String> feedbackDetailsList = Files.readAllLines(Paths.get(feedbackFile));
            for (String record : feedbackDetailsList){
                String[] rec = record.split("\\|");
                Feedback feedback = new Feedback();
                feedback.setFeedbackID(rec[0]);
                feedback.setFeedbackDateTime(rec[1]);
                feedback.setFeedbackTitle(rec[2]);
                feedback.setFeedbackContent(rec[3]);
                Customer customer = new Customer(rec[4], rec[5], rec[6]);
                feedback.setCustomer(customer);
                feedback.setManagingStaffID(rec[7]);
                feedbackList.add(feedback);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return feedbackList;
    }

    protected String getFeedbackID() {
        return feedbackID;
    }

    private void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    private String getFeedbackDateTime() {
        return feedbackDateTime;
    }

    private void setFeedbackDateTime(String orderDateTime) {
        this.feedbackDateTime = orderDateTime;
    }

    private String getFeedbackTitle() {
        return feedbackTitle;
    }

    private void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    private String getFeedbackContent() {
        return feedbackContent;
    }

    private void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    private Customer getCustomer() {
        return customer;
    }

    private void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private String getManagingStaffID() {
        return managingStaffID;
    }

    private void setManagingStaffID(String managingStaffID) {
        this.managingStaffID = managingStaffID;
    }

    @Override
    public String toString() {
        return  "Feedback Date Time: " + feedbackDateTime + '\n' +
                "Feedback Title: " + feedbackTitle + '\n' +
                "Feedback Content: " + feedbackContent + '\n' +
                "Customer ID: " + customer.getCustID() + '\n' +
                "Customer Name: " + customer.getCustName() + '\n' +
                "Customer Phone Number: " + customer.getCustPhoneNo() + '\n' +
                "Managing Staff ID: " + managingStaffID;
    }
}
