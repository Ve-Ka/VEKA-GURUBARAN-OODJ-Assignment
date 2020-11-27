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

public class Account {
    private String empID;
    private String empPassword;
    protected final static String empCredentialFile = "txt Files/empCredential.txt";

    public Account(){}

    public Account(String empID, String empPassword){
        this.empID = empID;
        this.empPassword = empPassword;
    }

    protected List<Account> getAllEmpCredential(){
        List<Account> accountList = new ArrayList();
        try {
            List<String> empCredentialList = Files.readAllLines(Paths.get(empCredentialFile));
            for (String record : empCredentialList){
                String[] rec = record.split("\\|");
                Account account = new Account();
                account.setEmpID(rec[0]);
                account.setEmpPassword(rec[1]);
                accountList.add(account);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return accountList;
    }

    protected void generateReport(){
        try {
            FileReader in = new FileReader(empCredentialFile);
            BufferedReader br = new BufferedReader(in);
            String record;
            ArrayList<Account> item = new ArrayList();
            while((record = br.readLine())!= null){
                String[] split = record.split("\\|");
                Account processed = new Account(split[0], split[1]);
                item.add(processed);
            }
            br.close();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("Generated Report/Account Report.pdf"));
            doc.open();
            doc.add(new Paragraph("Account Report", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD)));
            doc.add(new Paragraph(" "));

            // Defining Column Name
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Employee ID");
            table.addCell("Employee Password");

            for(Account object : item){
                table.addCell(object.getEmpID());
                table.addCell(object.getEmpPassword());
            }
            doc.add(table);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Feedback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    protected String getEmpID() {
        return empID;
    }

    protected void setEmpID(String empID) {
        this.empID = empID;
    }

    protected String getEmpPassword() {
        return empPassword;
    }

    protected void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    @Override
    public String toString() {
        return "ID: " + empID + '\n' +
                "Password: " + empPassword;
    }
}
