package Managers;

import Actions.Clicker;
import Actions.Selector;
import Managers.Exceptions.FieldNotFoundException;
import Managers.Exceptions.NotValidOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 *     {@code FormManager} will do the management of form. By adding fields, checkboxes and other web elements to feeding
 *     data into them all will be done very easily.
 * </p>
 *
 * @author HENIL
 * @since 04-11-22
 * @version 1.0
 * */
public class FormManager {
    WebDriver driver;
    TreeMap<String, By> webFields;
    TreeMap<String, By> webDropdowns;
    TreeMap<String, By> eventButtons;
    Clicker mouse;

    public static String DROPDOWN_BY_VALUES = "SELECTION_BY_VALUES";
    public static String DROPDOWN_BY_INDEX = "SELECTION_BY_INDEX";
    public static String DROPDOWN_BY_VISIBLE_TEXT = "SELECTION_BY_VISIBLE_TEXT";
    public static String DROPDOWN_SELECTION = FormManager.DROPDOWN_BY_VALUES;

    public FormManager(WebDriver driver) {
        this.driver = driver;
        this.webFields = new TreeMap<>();
        this.eventButtons = new TreeMap<>();
        this.webDropdowns = new TreeMap<>();
        this.mouse = new Clicker(this.driver);
    }

    public void addField(String identifier, By elementFinder) {
        if (this.webFields.get(identifier)!=null) {
            System.out.println("The identifier "+identifier+" already exist! It will be updated.");
        }
        this.webFields.put(identifier,elementFinder);
    }

    public void addDropdown(String identifier, By element) {
        if (this.webDropdowns.get(identifier)!=null) {
            System.out.println("The identifier "+identifier+" already exist! It will be updated.");
        }
        this.webDropdowns.put(identifier,element);
    }

    public void addButton(String identifier, By elementFinder) {
        if (this.webFields.get(identifier)!=null) {
            System.out.println("The identifier "+identifier+" already exist! It will be updated.");
        }
        this.eventButtons.put(identifier,elementFinder);
    }

    public void insertData(TreeMap<String, String> dataMapping) throws FieldNotFoundException {
        for (Map.Entry<String, String> data : dataMapping.entrySet()) {
            if (this.webFields.get(data.getKey())==null) {
                throw new FieldNotFoundException();
            } else {
                WebElement field = this.driver.findElement(this.webFields.get(data.getKey()));
                field.sendKeys(data.getValue());
            }
        }
    }

    public void insertDataIn(String key, String data) {
        TreeMap<String, String> dataMapping = new TreeMap<>();
        dataMapping.put(key,data);
        try {
            this.insertData(dataMapping);
        } catch (FieldNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectDropdowns(LinkedHashMap<String, String> dataMapping, String option, boolean delay) throws FieldNotFoundException, NotValidOption {
        FormManager.DROPDOWN_SELECTION = option;
        for (Map.Entry<String, String> data : dataMapping.entrySet()) {
            if (this.webDropdowns.get(data.getKey())==null) {
                throw new FieldNotFoundException();
            } else {
                Selector dropdown = new Selector(this.driver,this.webDropdowns.get(data.getKey()));
                if (FormManager.DROPDOWN_SELECTION.compareTo(FormManager.DROPDOWN_BY_VISIBLE_TEXT)==0) {
                    dropdown.Select_Ui.selectByVisibleText(data.getValue());
                } else if (FormManager.DROPDOWN_SELECTION.compareTo(FormManager.DROPDOWN_BY_VALUES)==0) {
                    dropdown.Select_Ui.selectByValue(data.getValue());
                } else if (FormManager.DROPDOWN_SELECTION.compareTo(FormManager.DROPDOWN_BY_INDEX)==0) {
                    dropdown.Select_Ui.selectByIndex(Integer.parseInt(data.getValue()));
                } else {
                    throw new NotValidOption("DROPDOWN_SELECTION");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void selectDropdowns(LinkedHashMap<String, String> dataMapping,String option) throws FieldNotFoundException, NotValidOption {
        this.selectDropdowns(dataMapping, option, false);
    }

    public void selectDropdowns(LinkedHashMap<String, String> dataMapping, boolean delay) throws FieldNotFoundException, NotValidOption {
        this.selectDropdowns(dataMapping, FormManager.DROPDOWN_SELECTION, delay);
    }

    public void selectDropdowns(LinkedHashMap<String, String> dataMapping) throws FieldNotFoundException, NotValidOption {
        this.selectDropdowns(dataMapping, false);
    }

    public void selectDropdown(String key, String value,String option) {
        LinkedHashMap<String, String> mapping = new LinkedHashMap<>();
        mapping.put(key, value);
        try {
            this.selectDropdowns(mapping,option,false);
        } catch (FieldNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NotValidOption e) {
            throw new RuntimeException(e);
        }
    }

    public void clickOn(By button) {
        this.mouse.click(button);
    }

    public void clickOn(String key) throws FieldNotFoundException {
        if (this.eventButtons.get(key)==null) {
            throw new FieldNotFoundException("The button that you want to click is not found!");
        } else {
            this.mouse.click(this.eventButtons.get(key));
        }
    }
}
