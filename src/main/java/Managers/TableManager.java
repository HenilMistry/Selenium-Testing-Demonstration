package Managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableManager {
    WebDriver driver;
    WebElement table;
    By allRowsUnder;
    String[] formatter;
    public static String DEFAULT_FORMATTER = "%20s";

    public TableManager(WebDriver d, By by) {
        this.driver = d;
        this.table = driver.findElement(by);
    }

    public void setAllRowsUnder(By by) {
        this.allRowsUnder = by;
    }

    public void setFormatter(String[] formatter) {
        this.formatter = formatter;
    }

    public void printAllRows() {
        if (this.formatter.length >= 0 && this.allRowsUnder!=null) {
            this.printAllRows(this.allRowsUnder, this.formatter);
        } else {
            System.out.println("PLEASE SET THE ALL ROWS UNDER PATH WITH THE FORMATTER");
        }
    }

    public void printAllRows(By allRowsUnder, String[]  formatter) {
        this.formatter = formatter;
        List<WebElement> allRows = this.table.findElements(allRowsUnder);
        for (int i=1; i<allRows.size(); i++) {
            int formatting = 0;
            for (WebElement child : allRows.get(i).findElements(By.xpath("./child::*")))  {
                if (formatting == this.formatter.length) {
                    System.out.printf(DEFAULT_FORMATTER,child.getText());
                } else {
                    System.out.printf(this.formatter[formatting],child.getText());
                }
                formatting ++;
            }
            System.out.println();
        }
    }
}
