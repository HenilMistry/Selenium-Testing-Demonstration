package Managers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This will be very helpful when you want to use table in black-box testing...
 *
 * @last_modified 22nd October 2022
 * @author HENIL MISTRY
 * @time 20th October 2022
 * */
public class TableManager {
    WebDriver driver;
    WebElement table;
    By allRowsUnder;
    String[] formatter;
    public static String DEFAULT_FORMATTER = "%20s";
    ArrayList<ArrayList<String>> virtualRows;

    public TableManager(WebDriver d, By by) {
        this.driver = d;
        this.table = driver.findElement(by);
        this.virtualRows = new ArrayList<>();
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
        this.allRowsUnder = allRowsUnder;
        this.formatter = formatter;
        List<WebElement> allRows = this.table.findElements(allRowsUnder);
        for (int i=0; i<allRows.size(); i++) {
            int formatting = 0;
            for (WebElement child : allRows.get(i).findElements(By.xpath("./child::*")))  {
                if (formatting >= this.formatter.length) {
                    System.out.printf(DEFAULT_FORMATTER+"|",child.getText());
                } else {
                    System.out.printf(this.formatter[formatting]+"|",child.getText());
                }
                formatting ++;
            }
            System.out.println();
        }
    }

    private ArrayList<ArrayList<String>> getRowsData(By allRowsUnder) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        this.formatter = formatter;
        List<WebElement> allRows = this.table.findElements(allRowsUnder);
        for (int i=0; i<allRows.size(); i++) {
            ArrayList<String> row = new ArrayList<>();
            int formatting = 0;
            for (WebElement child : allRows.get(i).findElements(By.xpath("./child::*")))  {
                row.add(child.getText());
            }
            data.add(row);
        }
        return data;
    }

    /**
     * This function will add one row to the virtual row, this can be used later on for black-box testing...
     *
     * @param cols This will be the data in each column to make one row, that needs to be added in virtual rows
     * */
    public void addRow(ArrayList<String> cols) {
        ArrayList<String> data = new ArrayList<>();
        data.addAll(cols);
        this.virtualRows.add(data);
    }

    /**
     * This function will help you to add more than one rows at a single function call.
     *
     * @param list This will be the list of rows that you want to be added in virtual rows for later debugging...
     * */
    public void addRows(ArrayList<ArrayList<String>> list) {
        for(ArrayList<String> cols : list) {
            this.addRow(cols);
        }
    }

    public void verifyLastRows(int count) {
        this.verifyLastRows(this.virtualRows,count);
    }

    public void verifyLastRows(ArrayList<ArrayList<String>> testData,int count) {
        this.verifyLastRows(testData,this.allRowsUnder,count);
    }

    public void verifyLastRows(ArrayList<ArrayList<String>> testData,By allRowsUnder, int count) {
        this.allRowsUnder = allRowsUnder;
        ArrayList<ArrayList<String>> actualData = this.getRowsData(allRowsUnder);

        int index = 0;
        boolean flag = true;
        for (int i=actualData.size()-count; i<actualData.size(); i++) {
            if (flag) {
                if (actualData.get(i).size()!=testData.get(index).size()) {
                    System.out.printf("---: WARNING : COLUMNS NOT MATCHED ON INDEX %d :---\n",index);
                    int limit = (actualData.get(i).size()>testData.get(index).size())?(testData.get(index).size()):(actualData.get(i).size());
                    for (int j=0; j<limit; j++) {
                        if (actualData.get(i).get(j).compareTo(testData.get(index).get(j))!=0) {
                            System.out.printf("ACTUAL %s UNMATCHED WITH TEST %s\n",actualData.get(i).get(j),testData.get(index).get(j));
                            flag = false;
                            break;
                        }
                    }
                } else {
                    for (int j=0; j<actualData.get(i).size(); j++) {
                        if (actualData.get(i).get(j).compareTo(testData.get(index).get(j))!=0) {
                            System.out.printf("ACTUAL %s UNMATCHED WITH TEST %s\n",actualData.get(i).get(j),testData.get(index).get(j));
                            flag = false;
                            break;
                        }
                    }
                }
                index++;
            }
        }
        System.out.println((flag)?"ALL ROWS VALID":"NOT VALID, FOUND MISMATCH!");
    }
}
