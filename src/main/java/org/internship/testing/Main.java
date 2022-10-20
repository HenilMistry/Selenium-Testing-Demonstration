package org.internship.testing;

import Actions.Clicker;
import Actions.Selector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class Main {

    private static String DRIVER_PATH = "E:\\Software\\chromedriver.exe";
    private static String SITE_URL = "https://henilmistry.github.io/Internship-Tasks/";
    private static String EMP_MANAGER_BTN = "/html/body/div/div/button[1]";
    private static String COLOR_MAGIC_BTN = "/html/body/div/div/button[2]";
    private static String ADD_EMP_BTN = "add";

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

        WebElement[] form_fields = new WebElement[] {
                driver.findElement(By.id(ADD_EMP_FORM[0])),
                driver.findElement(By.id(ADD_EMP_FORM[1])),
                driver.findElement(By.id(ADD_EMP_FORM[2])),
                driver.findElement(By.id(ADD_EMP_FORM[4]))
        };
        Selector dropdown = new Selector(driver, By.id(ADD_EMP_FORM[3]));

        Random r = new Random();
        String id = String.valueOf(r.nextInt(100));
        String name = ADD_EMP_DATA[0][r.nextInt(ADD_EMP_DATA[0].length)];
        String mail = ADD_EMP_DATA[1][r.nextInt(ADD_EMP_DATA[1].length)];
        dropdown.Select_Ui.selectByVisibleText(DESIGNATIONS[r.nextInt(DESIGNATIONS.length)]);
        String salary = ADD_EMP_DATA[2][r.nextInt(ADD_EMP_DATA[2].length)];
        String[] values = new String[] {
                id, name, mail, salary
        };
        for(int j=0; j<r.nextInt(5); j++) {
            for(int i=0; i< form_fields.length; i++) {
                form_fields[i].sendKeys(values[i]);
            }
            mouse.click(By.id(ADD_EMP_BTN));
            Thread.sleep(2000);
        }

        // closing and quiting...
        driver.close();
        driver.quit();
    }
}