package org.internship.testing;

import Actions.Clicker;
import Actions.Selector;
import Managers.TableManager;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataInsertionTest {

    public static String DRIVER_PATH = "E:\\Software\\chromedriver.exe";
    public static String SITE_URL = "https://henilmistry.github.io/Internship-Tasks/";
    public static String EMP_MANAGER_BTN = "/html/body/div/div/button[1]";
    private static String COLOR_MAGIC_BTN = "/html/body/div/div/button[2]";
    private static String ADD_EMP_BTN = "add";
    private static String EMP_TABLE = "tbl_details";

    private static String[] ADD_EMP_FORM = new String[] {
      "emp_id","emp_name","emp_mail","emp_designation","emp_salary"
    };

    private static String[] DESIGNATIONS = new String[] {
            "HR", "Employee", "Intern"
    };

    private static String[][] ADD_EMP_DATA = new String[][] {
            {"Heal Mistry", "Henil Mistry", "Alka Mistry", "Nidhi Lad", "Divya Prajapati", "Rakeshbhai Mistry", "Kaltilal Mistry", "Deep Dhadhuk"},
            {"healmistry@gmail.com", "alka13051980@gmail.com","nidhi@princess.com", "divya@yahoo.co.in", "Kanti@ambaram.co.in"},
            {"12000","15000","20000","30000","5000","6000","2000","3000","50000","80000","60000"}
    };

    public static void main(String[] args) throws InterruptedException {
        // set chrome driver...
        System.setProperty("webdriver.chrome.driver",DRIVER_PATH);
        // get the instance of driver...
        WebDriver driver = new ChromeDriver();
        // get the instance of clicker...
        Clicker mouse = new Clicker(driver);
        // maximize the window...
        driver.manage().window().maximize();

        // load the site first...
        driver.get(SITE_URL);
        // click on the employee manager button...
        mouse.click(By.xpath(EMP_MANAGER_BTN));
        // fill the form for adding new employee...

        // all ids...
        ArrayList<Integer> all_id = new ArrayList<>();
        // all rows data...
        ArrayList<ArrayList<String>> rows = new ArrayList<>();

        // adding random five employees...
        for (int j=0; j<5; j++) {
            ArrayList<String> row = new ArrayList<>();
            try {
                WebElement[] form_fields = new WebElement[] {
                        driver.findElement(By.id(ADD_EMP_FORM[0])),
                        driver.findElement(By.id(ADD_EMP_FORM[1])),
                        driver.findElement(By.id(ADD_EMP_FORM[2])),
                        driver.findElement(By.id(ADD_EMP_FORM[4]))
                };
                Selector dropdown = new Selector(driver, By.id(ADD_EMP_FORM[3]));

                Random r = new Random();
                int id_no = 5-j;

                while (all_id.contains(id_no)) {
                    id_no = r.nextInt(100);
                }
                all_id.add(id_no);
                String id = String.valueOf(id_no);
                String name = ADD_EMP_DATA[0][r.nextInt(ADD_EMP_DATA[0].length)];
                String mail = ADD_EMP_DATA[1][r.nextInt(ADD_EMP_DATA[1].length)];
                String designation = DESIGNATIONS[r.nextInt(DESIGNATIONS.length)];
                dropdown.Select_Ui.selectByVisibleText(designation);
                String salary = ADD_EMP_DATA[2][r.nextInt(ADD_EMP_DATA[2].length)];
                String[] values = new String[] {
                        id, name, mail, salary
                };
                row.add(id);
                row.add(name);
                row.add(mail);
                row.add(designation);
                row.add(salary);
                rows.add(0, row);
                Thread.sleep(1000);
                for(int i=0; i< form_fields.length; i++) {
                    form_fields[i].sendKeys(values[i]);
                }
                mouse.click(By.id(ADD_EMP_BTN));
            } catch (UnhandledAlertException e) {
                Thread.sleep(2000);
                driver.switchTo().alert().dismiss();
            } finally {
                continue;
            }
        }

        // creating table manager's object...
        TableManager manager = new TableManager(driver,By.id(EMP_TABLE));
        String[] formatter = new String[] {
                "%20s","%20s","%30s","%20s","%20s"
        };

        // printing all rows...
        manager.printAllRows(By.xpath("./child::thead/*"),formatter);
        // verifying all rows...
        manager.verifyLastRows(rows,By.xpath("./child::thead/*"),rows.size());

        Scanner scan = new Scanner(System.in);
        Character c = scan.next().charAt(0);

        // closing and quiting...
        driver.close();
        driver.quit();
    }
}