package Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Clicker {
    WebDriver driver;

    public Clicker(WebDriver d) {
        this.driver = d;
    }

    public Clicker(WebDriver d, By by) {
        this.driver = d;
        click(by);
    }

    public void click(By by) {
        WebElement element = this.driver.findElement(by);
        element.click();
    }
}
