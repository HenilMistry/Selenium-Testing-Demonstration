package SeleniumProjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.nio.Buffer;
import java.util.Scanner;

public class WhatsAppAutomation {

    public static String DRIVER_PATH = "E:\\Software\\chromedriver.exe";
    public static String SITE_URL = "https://web.whatsapp.com/";
    public static String SEARCH_BAR = "//*[@id=\"side\"]/div[1]/div/div/div[2]/div/div[2]";
    public static String MESSAGE_BOX = "//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p";
    public static String SEND_BUTTON = "//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button/span";

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        // go to whatsapp...
        driver.get(SITE_URL);

        // wait until I scan the QRCode to login...
        new Scanner(System.in).next().charAt(0);

        try {
            BufferedReader scan = new BufferedReader(new FileReader("D:\\Project\\SeleniumTesting\\InternshipTask-Testing\\src\\main\\java\\SeleniumProjects\\Files\\WhatsApp.txt"));

            String row = scan.readLine();
            while (row!=null) {
                String[] columns  = row.split(":");

                WebElement search_bar = driver.findElement(By.xpath(SEARCH_BAR));
                search_bar.sendKeys(columns[0]);

                Thread.sleep(1000);
                WebElement profile = driver.findElement(By.className("_3OvU8"));
                profile.click();

                WebElement message_box = driver.findElement(By.xpath(MESSAGE_BOX));
                message_box.sendKeys(columns[2]);

                WebElement send_button = driver.findElement(By.xpath(SEND_BUTTON));
                send_button.click();

                System.out.println("Message sent to "+columns[1]);
                row = scan.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Scanner(System.in).next().charAt(0);
        driver.close();
        driver.quit();
    }
}
