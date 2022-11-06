package SeleniumProjects;

import Managers.Exceptions.FieldNotFoundException;
import Managers.Exceptions.NotValidOption;
import Managers.FormManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class ResultFetchingForCharusat {
    public static String DRIVER_PATH = "E:\\Software\\chromedriver.exe";
    public static String SITE_URL = "https://charusat.edu.in:912/Uniexamresult/";
    public static String[] dropdownKeys = new String[] {
      "DD_INSTITUTE","DD_DEGREE","DD_SEMESTER","DD_EXAM"
    };
    public static String[] dropdownIds = new String[] {
      "ddlInst","ddlDegree","ddlSem","ddlScheduleExam",
    };
    public static String idFieldKey = "STUDENT_ID";
    public static String idFieldId = "txtEnrNo";

    public static String submitBtnKey = "SHOW_MARKSHEET_BTN";
    public static String submitBtnId = "btnSearch";

    public static String SGPA_ID = "uclGrd1_lblSGPA";
    public static String CGPA_ID = "uclGrd1_lblCGPA";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        driver.get(SITE_URL);
        FormManager markSheetForm = new FormManager(driver);

        // adding form fields...
        // adding all the dropdowns...
        for (int i=0; i<dropdownKeys.length; i++) {
            markSheetForm.addDropdown(dropdownKeys[i],By.id(dropdownIds[i]));
        }
        // adding one field for student id...
        markSheetForm.addField(idFieldKey,By.id(idFieldId));
        // adding submit button...
        markSheetForm.addButton(submitBtnKey,By.id(submitBtnId));

        // adding dropdown data...
        String[] data = new String[] {
          "CSPIT","BTECH(CE)","4","MAY 2022"
        };
        LinkedHashMap<String, String> dropdownData = new LinkedHashMap<>();
        for (int i=0; i< dropdownKeys.length; i++) {
            dropdownData.put(dropdownKeys[i], data[i]);
        }

        File results = new File("SEM4_Result.txt");
        FileWriter writer;
        try {
            writer = new FileWriter(results);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i=1; i<5; i++) {
            try {
                String rollno = String.format("20CE%03d",i);
                markSheetForm.selectDropdowns(dropdownData, FormManager.DROPDOWN_BY_VISIBLE_TEXT);
                markSheetForm.insertDataIn(idFieldKey, rollno);
                markSheetForm.clickOn(submitBtnKey);
                Thread.sleep(1000);
                WebElement sgpa = driver.findElement(By.id(SGPA_ID));
                WebElement cgpa = driver.findElement(By.id(CGPA_ID));
                writer.write(rollno+" "+sgpa.getText()+" "+cgpa.getText()+"\n");
                driver.get(SITE_URL);
            } catch (FieldNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NotValidOption e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Scanner(System.in).next().charAt(0);

        driver.close();
        driver.quit();
    }
}
