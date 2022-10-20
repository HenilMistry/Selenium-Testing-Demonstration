package Actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Selector {
    WebDriver driver;
    WebElement element;
    public Select Select_Ui;

    public Selector(WebDriver d, By by) {
        this.driver = d;
        this.element = this.driver.findElement(by);
        this.Select_Ui = new Select(this.element);
    }
}
