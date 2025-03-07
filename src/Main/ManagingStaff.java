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


public class ManagingStaff extends Employee implements MiscellaneousFunction{
    private final static String managingStaffDetailsFile = "txt Files/managingStaffDetails.txt";

    public ManagingStaff(){}

    public ManagingStaff(String empID){
        super(empID);
    }

    public ManagingStaff(String empID, String empPassword) {
        super(empID, empPassword);
    }

    public ManagingStaff(String empID, String empName, int empAge, String empGender, String empEmail) {
        super(empID, empName, empAge, empGender, empEmail);
    }

    @Override
    protected void displayStaffDetails(String empID) {
        List<ManagingStaff> managingStaffDetailsList = getAllManagingStaffDetails();
        for (ManagingStaff detail: managingStaffDetailsList){
            if(detail.getEmpID().equals(empID)){
                System.out.println(detail.toString());
            }
        }
    }

    @Override
    public List<String> defaultDetails(String ID){
        List<ManagingStaff> originalDetails = getAllManagingStaffDetails();
        List<String> defaultDetails = new ArrayList<>();
        for (ManagingStaff detail : originalDetails) {
            if (detail.getEmpID().equals(ID)) {
                defaultDetails.add(detail.getEmpID());
                defaultDetails.add(detail.getEmpName());
                defaultDetails.add(Integer.toString(detail.getEmpAge()));
                defaultDetails.add(detail.getEmpGender());
                defaultDetails.add(detail.getEmpEmail());
            }
        }
        return defaultDetails;
    }

    @Override
    public String generateID(){
        List<ManagingStaff> defaultList = getAllManagingStaffDetails();
        String newEmpID;
        try{
            newEmpID = String.format("MS%04d", ((Integer.parseInt(defaultList.get(defaultList.size()
                    - 1).getEmpID().replaceAll("MS", ""))) + 1));
        }catch(IndexOutOfBoundsException e){
            newEmpID = "MS0001";
        }
        return newEmpID;
    }

    @Override
    public void generateReport() {
        try {
            FileReader in = new FileReader(managingStaffDetailsFile);
            BufferedReader br = new BufferedReader(in);
            String record;
            ArrayList<ManagingStaff> item = new ArrayList();
            while((record = br.readLine())!= null){
                String[] split = record.split("\\|");
                ManagingStaff processed = new ManagingStaff(split[0], split[1], Integer.parseInt(split[2]), split[3],
                        split[4]);
                item.add(processed);
            }
            br.close();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Generated Report/Managing Staff Report.pdf"));
            doc.open();
            doc.add(new Paragraph("Managing Staff Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD)));
            doc.add(new Paragraph(" "));

            // Defining Column Name
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Age");
            table.addCell("Gender");
            table.addCell("Email");

            for(ManagingStaff object : item){
                table.addCell(object.getEmpID());
                table.addCell(object.getEmpName());
                table.addCell(Integer.toString(object.getEmpAge()));
                table.addCell(object.getEmpGender());
                table.addCell(object.getEmpEmail());
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Feedback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void editStaffDetails(ManagingStaff managingStaff) {
        // Overwrite Original List with new data
        List<ManagingStaff> originalDetails = getAllManagingStaffDetails();
        int position = 0;
        for (ManagingStaff detail: originalDetails){
            if (detail.getEmpID().equals(managingStaff.getEmpID())) {
                break;
            }else{
                position ++;
            }
        }
        originalDetails.set(position, managingStaff);

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(managingStaffDetailsFile);
            for (ManagingStaff detail: originalDetails){
                WriteData.write(String.format("%s|%s|%d|%s|%s\n", detail.getEmpID(), detail.getEmpName(),
                        detail.getEmpAge(), detail.getEmpGender(), detail.getEmpEmail()));
            }
            WriteData.close();
            System.out.println("Alert: Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // modify all staff section
    protected List<ManagingStaff> getAllManagingStaffDetails(){
        List<ManagingStaff> managingStaffDetailsList = new ArrayList();
        try {
            List<String> empDetailsList = Files.readAllLines(Paths.get(managingStaffDetailsFile));
            for (String record : empDetailsList){
                String[] rec = record.split("\\|");
                ManagingStaff managingStaff = new ManagingStaff();
                managingStaff.setEmpID(rec[0]);
                managingStaff.setEmpName(rec[1]);
                managingStaff.setEmpAge(Integer.parseInt(rec[2]));
                managingStaff.setEmpGender(rec[3]);
                managingStaff.setEmpEmail(rec[4]);
                managingStaffDetailsList.add(managingStaff);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return managingStaffDetailsList;
    }

    protected void addEmpAccount(String newEmpID, String newEmpPassword, ManagingStaff managingStaff){
        try{
            // Write to Credential File
            FileWriter WriteData = new FileWriter(Account.empCredentialFile, true);
            WriteData.write(String.format("%s|%s\n", newEmpID, newEmpPassword));
            WriteData.close();

            // Write to Staff Details File
            WriteData = new FileWriter(managingStaffDetailsFile, true);
            WriteData.write(String.format("%s|%s|%d|%s|%s\n", managingStaff.getEmpID(), managingStaff.getEmpName(),
                    managingStaff.getEmpAge(), managingStaff.getEmpGender(), managingStaff.getEmpEmail()));
            WriteData.close();

            System.out.println("Alert: New Account and Details Added!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void addEmpAccount(String newEmpID, String newEmpPassword, DeliveryStaff deliveryStaff){
        try{
            // Write to Credential File
            FileWriter WriteData = new FileWriter(Account.empCredentialFile, true);
            WriteData.write(String.format("%s|%s\n", newEmpID, newEmpPassword));
            WriteData.close();

            // Write to Staff Details File
            WriteData = new FileWriter(DeliveryStaff.deliveryStaffDetailsFile, true);
            WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", deliveryStaff.getEmpID(),
                    deliveryStaff.getEmpName(), deliveryStaff.getEmpAge(), deliveryStaff.getEmpGender(),
                    deliveryStaff.getEmpEmail(), deliveryStaff.getVehicleBrand(), deliveryStaff.getVehiclePlateNo()));
            WriteData.close();
            System.out.println("Alert: New Account and Details Added!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void removeEmpAccount(String empID){
        // Remove Account from Account Object List
        Account accountTemp = new Account();
        List<Account> accounts = accountTemp.getAllEmpCredential();
        accounts.removeIf(account -> account.getEmpID().equals(empID));

        // Remove Details from Details Object List
        List<ManagingStaff> managingStaffs = getAllManagingStaffDetails();
        List<DeliveryStaff> deliveryStaffs = DeliveryStaff.getAllDeliveryStaffDetails();
        if (empID.contains("MS")){
            managingStaffs.removeIf(managingStaff -> managingStaff.getEmpID().equals(empID));
        }else if (empID.contains("DS")){
            deliveryStaffs.removeIf(deliveryStaff -> deliveryStaff.getEmpID().equals(empID));
        }

        // Write all data to file

        try {
            FileWriter WriteData = new FileWriter(Account.empCredentialFile);
            for (Account account: accounts){
                WriteData.write(String.format("%s|%s\n", account.getEmpID(), account.getEmpPassword()));
            }
            WriteData.close();

            if(empID.contains("MS")){
                WriteData = new FileWriter(managingStaffDetailsFile);
                for (ManagingStaff managingStaff: managingStaffs){
                    WriteData.write(String.format("%s|%s|%d|%s|%s\n", managingStaff.getEmpID(),
                            managingStaff.getEmpName(), managingStaff.getEmpAge(), managingStaff.getEmpGender(),
                            managingStaff.getEmpEmail()));
                }
            }else if(empID.contains("DS")){
                WriteData = new FileWriter(DeliveryStaff.deliveryStaffDetailsFile);
                for (DeliveryStaff deliveryStaff: deliveryStaffs){
                    WriteData.write(String.format("%s|%s|%d|%s|%s|%s|%s\n", deliveryStaff.getEmpID(),
                            deliveryStaff.getEmpName(), deliveryStaff.getEmpAge(), deliveryStaff.getEmpGender(),
                            deliveryStaff.getEmpEmail(), deliveryStaff.getVehicleBrand(),
                            deliveryStaff.getVehiclePlateNo()));
                }
            }

            WriteData.close();
            System.out.println("Alert: Employee Account and Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // modify customer section
    protected void addCustDetails(Customer customer){
        try{
            // Write to Customer File
            FileWriter WriteData = new FileWriter(Customer.custDetailsFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%s\n", customer.getCustID(), customer.getCustName(),
                    customer.getCustEmail(), customer.getCustPhoneNo(), customer.getCustAddress()));
            WriteData.close();
            System.out.println("Alert: New Customer Added!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void editCustDetails(Customer customer){
        // Overwrite Original List with new data
        Customer customer1 = new Customer();
        List<Customer> originalDetails = customer1.getAllCustDetails();
        int position = 0;
        for (Customer detail: originalDetails){
            if (detail.getCustID().equals(customer.getCustID())) {
                break;
            }else{
                position ++;
            }
        }
        originalDetails.set(position, customer);

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(Customer.custDetailsFile);
            for (Customer detail : originalDetails) {
                WriteData.write(String.format("%s|%s|%s|%s|%s\n", detail.getCustID(), detail.getCustName(),
                        detail.getCustEmail(), detail.getCustPhoneNo(), detail.getCustAddress()));
            }
            WriteData.close();
            System.out.println("Alert: Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void removeCustDetails(String custID){
        // Remove Details from Customer Object List
        Customer customer = new Customer();
        List<Customer> customers = customer.getAllCustDetails();
        customers.removeIf(customer1 -> customer1.getCustID().equals(custID));

        // Write all data to file
        try {
            FileWriter WriteData = new FileWriter(Customer.custDetailsFile);
            for (Customer detail : customers) {
                WriteData.write(String.format("%s|%s|%s|%s|%s\n", detail.getCustID(), detail.getCustName(),
                        detail.getCustEmail(), detail.getCustPhoneNo(), detail.getCustAddress()));
            }
            WriteData.close();
            System.out.println("Alert: Customer Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // modify item section
    protected void addItemDetails(Item item){
        try{
            // Write to Customer File
            FileWriter WriteData = new FileWriter(Item.itemDetailsFile, true);
            WriteData.write(String.format("%s|%s|%s|%s|%s|%s\n", item.getItemID(), item.getItemName(),
                    item.getItemQuantity(), item.getItemPrice(), item.getItemSupplier(), item.getItemDescription()));
            WriteData.close();
            System.out.println("Alert: New Item Added!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void editItemDetails(Item item){
        // Overwrite Original List with new data
        Item item1 = new Item();
        List<Item> originalDetails = item1.getAllItemDetails();
        int position = 0;
        for (Item detail: originalDetails){
            if (detail.getItemID().equals(item.getItemID())) {
                break;
            }else{
                position ++;
            }
        }
        originalDetails.set(position, item);

        // Write to file
        try{
            FileWriter WriteData = new FileWriter(Item.itemDetailsFile);
            for (Item detail : originalDetails) {
                WriteData.write(String.format("%s|%s|%d|%.2f|%s|%s\n", detail.getItemID(), detail.getItemName(),
                        detail.getItemQuantity(), detail.getItemPrice(), detail.getItemSupplier(),
                        detail.getItemDescription()));
            }
            WriteData.close();
            System.out.println("Alert: Details Updated!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void removeItemDetails(String itemID){
        // Remove Details from Customer Object List
        Item item = new Item();
        List<Item> items = item.getAllItemDetails();
        items.removeIf(item1 -> item1.getItemID().equals(itemID));

        // Write all data to file
        try{
            FileWriter WriteData = new FileWriter(Item.itemDetailsFile);
            for (Item detail : items) {
                WriteData.write(String.format("%s|%s|%d|%.2f|%s|%s\n", detail.getItemID(), detail.getItemName(),
                        detail.getItemQuantity(), detail.getItemPrice(), detail.getItemSupplier(),
                        detail.getItemDescription()));
            }
            WriteData.close();
            System.out.println("Alert: Item Details Removed!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
