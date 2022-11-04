# Selenium-Testing-Demonstration
This repository contains the testing of software using Selenium Tool with Java Language.
I have created the Packages to help with the testing. Mainly I have created packages as below.

## Packages Included
### Actions
- #### Clicker Class
  - It helps to mimic click action on any **'WebElement'** that you may need to perform. It will accept WebDriver as
    constructor, and you can use `click()` method to pass `By` as a parameter, and you can perform click event by just
    following below code.

    ```
        // get the instance of driver...
        WebDriver driver = new ChromeDriver();
        // get the instance of clicker...
        Clicker mouse = new Clicker(driver);
        // click on the employee manager button...
        mouse.click(By.xpath("<xpath_string_of_element>"));
    ```    

- #### Selector Class
    - It will be helpful to when you need to deal with dropdown menus in forms. It is most likely to be seen that many forms
    includes dropdown in it. To easily deal with `Dropdown` or `select` you can use this class to write less code and be productive.
    
    ```
    // get the instance of driver...
    WebDriver driver = new ChromeDriver();
    // make the dropdown's object by passing the element's x path
    // you can pass By.xpath() also or anyother By's instance...
    Selector dropdown = new Selector(driver, By.id("<id_of_element>"));
    // then you can manipulate the dropdown by using Select_UI
    // this will select the "Hello" option
    dropdown.Select_Ui.selectByVisibleText("Hello");
    ```
### Managers 
- Table Manager
  - It will be very helpful when you need to deal with tables in the Blackbox testing. Any application will take i/p as data
  and will produce some o/p, as a human by our eyes we just observe the o/p, but we talk about computers. They cannot do that
  so this class will help you achieve that vision of dealing with tables.

  - Initial construction of table manager's object will require driver and main table's reference using `By`

  ```
    // instance of web driver...
    WebDriver driver = new ChromeDriver();
    // initialzing table manger's object...
    // you can also pass By.xpath() reference or anyother kind of By's object
    TableManager manager = new TableManager(driver,By.id("<table_id>"));
  ```  
  
  - To print all data inside the table, you can use below lines...
  
  ```
    // specify the fomatting for each of the columns in table to visualize easily...
    String[] formatter = new String[] {
                "%20s","%20s","%30s","%20s","%20s"
        };
    // use printAllRows() method...
    // it will loop through all tr tags and inside all tr, it will fetch data from all td tags
    manager.printAllRows(By.xpath("./child::thead/*"),formatter);
  ```
