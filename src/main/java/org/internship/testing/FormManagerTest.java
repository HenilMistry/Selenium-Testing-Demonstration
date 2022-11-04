package org.internship.testing;

import Managers.Exceptions.FieldNotFoundException;
import Managers.Exceptions.NotValidOption;
import Managers.FormManager;
import Managers.TableManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;
import java.util.TreeMap;

public class FormManagerTest {

    public static String[] keyFields = new String[] {
      "EMP_ID","EMP_NAME","EMP_MAIL","EMP_SALARY"
    };

    public static String[] idsFields = new String[] {
      "emp_id", "emp_name", "emp_mail", "emp_salary"
    };

    public static String[] keyDropdowns = new String[] {
      "EMP_DESIGNATION"
    };

    public static String[] idsDropdowns = new String[] {
      "emp_designation"
    };

    public static String[] keyButtons = new String[] {
            "ADD_EMP"
    };

    public static String[] idsButton = new String[] {
            "add"
    };

    public static String EMP_TABLE = "tbl_details";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",DataInsertionTest.DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        driver.get(DataInsertionTest.SITE_URL);


        // get the instance of form manager...
        FormManager empDetails = new FormManager(driver);
        empDetails.clickOn(By.xpath(DataInsertionTest.EMP_MANAGER_BTN));

        // add all those fields which requires the input...
        for (int i=0; i< keyFields.length; i++) {
            empDetails.addField(keyFields[i], By.id(idsFields[i]));
        }
        empDetails.addDropdown(keyDropdowns[0], By.id(idsDropdowns[0]));
        empDetails.addButton(keyButtons[0], By.id(idsButton[0]));

        // make data to be inserted...
        String[] data = new String[] {
          "1","Henil Mistry", "henilmistry@ieee.org", "50000"
        };
        TreeMap<String, String> keyToDataMap = new TreeMap<>();
        for (int i=0; i< keyFields.length; i++) {
            keyToDataMap.put(keyFields[i], data[i]);
        }

        // insert data...
        try {
            empDetails.insertData(keyToDataMap);
        } catch (FieldNotFoundException e) {
            throw new RuntimeException(e);
        }

        // select DROPDOWN_SELECTION property as you want...
        FormManager.DROPDOWN_SELECTION = FormManager.DROPDOWN_BY_VISIBLE_TEXT;
        // select dropdown...
        TreeMap<String, String> keyToDropdown = new TreeMap<>();
        keyToDropdown.put(keyDropdowns[0], "Intern");
        try {
            empDetails.selectDropdowns(keyToDropdown);
            // click the button...
            empDetails.clickOn(keyButtons[0]);
        } catch (FieldNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NotValidOption e) {
            throw new RuntimeException(e);
        }

        // creating table manager's object...
        TableManager manager = new TableManager(driver,By.id(EMP_TABLE));
        String[] formatter = new String[] {
                "%20s","%20s","%30s","%20s","%20s"
        };

        // printing all rows...
        manager.printAllRows(By.xpath("./child::thead/*"),formatter);

        new Scanner(System.in).next().charAt(0);

        driver.close();
        driver.quit();
    }
}
