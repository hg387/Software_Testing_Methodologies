// Based on the example code at http://www.seleniumhq.org/docs/03_webdriver.jsp
package edu.drexel.se320;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SeleniumTest {
    @BeforeAll
    static void setGeckoPath(){
        // set the gecko driver path accordingly
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\hg387\\Downloads\\TASE181\\geckodriver-v0.28.0-win64\\geckodriver.exe");
    }

    // set the file path accordingly
    protected final String uiPath = "file:///C:/Users/hg387/Downloads/TASE181/hw5/hw5/web/index.html";

    // Test for checking minus button before adding/deleting any todos.
    // plus button gets invisible after clicked
    // minus button gets visible after plus clicked
    // input gets visible after plus clicked
    @Test
    public void testControlOneMinus() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            WebElement elt = driver.findElement(By.id("controls1plus"));

            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));
            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // Test for checking addition of one item
    // clicked plus button -> entered item 1 -> clicked Add button
    // new item should be visible on the page after addition
    // description of new item should get matched
    public void testAddOneItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();


            WebElement li = driver.findElement(By.id("item1"));
            assertTrue(li.isDisplayed());
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");
        } finally {
            driver.quit();
        }
    }

    @Test
    // Test for checking plus minus events after addition of one item
    // clicked plus button -> entered item 1 -> clicked Add button
    // new item should be visible on the page after addition
    // description of new item should get matched
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testCheckPlusMinusAfterAddingOneItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();


            WebElement li = driver.findElement(By.id("item1"));
            assertTrue(li.isDisplayed());
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");


            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // test for addition of two items
    // clicked plus button -> entered item 1 -> clicked Add button -> entered item 2 -> clicked Add button
    // new items should be visible on the page after addition
    // description of new items should get matched
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testAddTwoItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));


            elt.click();


            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            input.clear();
            input.sendKeys("New item added");
            addButton.click();


            WebElement li = driver.findElement(By.id("item1"));
            WebElement li1 = driver.findElement(By.id("item2"));

            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");
            assertTrue(li1.getText().startsWith("New item added"), "Checking correct text for added element");

            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // test for deletion of one item when only one item is present
    // clicked plus button -> entered item 1 -> clicked Add button -> clicked delete button
    // check if item is visible after deletion
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testDeleteOneItem() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);

            WebElement elt = driver.findElement(By.id("controls1plus"));

            elt.click();

            WebElement input = driver.findElement(By.id("itemtoadd"));

            input.sendKeys("Something to do");


            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();


            WebElement deleteButton1 = driver.findElement(By.id("button1"));
            deleteButton1.click();

            List<WebElement> listOfElems = driver.findElements(By.id("item1"));

            List<WebElement> totalElemsli = driver.findElements(By.xpath("//span[@id='content']/ul//li"));

            assertEquals(totalElemsli.size(), 0);
            assertEquals(listOfElems.size(), 0);

            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // test for deletion of one item when only one item is present
    // clicked plus button -> entered item 1 -> clicked Add button -> -> entered item 2 -> clicked delete button item 1 -> clicked delete button item 2
    // check if items are visible after deletion
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testDeleteBothItems() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the form to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            input.clear();
            input.sendKeys("New item added");
            addButton.click();

            WebElement deleteButton1 = driver.findElement(By.id("button1"));
            deleteButton1.click();
            WebElement deleteButton2 = driver.findElement(By.id("button2"));
            deleteButton2.click();

            List<WebElement> listOfElemsli1 = driver.findElements(By.id("item1"));
            List<WebElement> listOfElemsli2 = driver.findElements(By.id("item2"));
            // find all elements with li tag
            List<WebElement> totalElemslis = driver.findElements(By.xpath("//span[@id='content']/ul//li"));



            assertEquals(listOfElemsli1.size(), 0);
            assertEquals(listOfElemsli2.size(), 0);
            assertEquals(totalElemslis.size(), 0);

            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // test for deleting first item out of two
    // item 1 should get invisible
    // item 2 should remain visible
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testDeleteFirstItemOutofTwo() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the form to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            input.clear();
            input.sendKeys("New item added");
            addButton.click();

            WebElement deleteButton1 = driver.findElement(By.id("button1"));
            deleteButton1.click();

            List<WebElement> listOfElems = driver.findElements(By.id("item1"));
            // find all elements with li tag
            List<WebElement> totalElemsli = driver.findElements(By.xpath("//span[@id='content']/ul//li"));

            WebElement li1 = driver.findElement(By.id("item2"));
            // We use startsWith because getText includes the text of the Delete button
            assertEquals(listOfElems.size(), 0);
            assertEquals(totalElemsli.size(), 1);
            assertTrue(li1.getText().startsWith("New item added"), "Checking correct text for added element");

            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // test for deleting second item out of two
    // item 2 should get invisible
    // item 1 should remain visible
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testDeleteSecondItemOutOfTwo() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the form to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            input.clear();
            input.sendKeys("New item added");
            addButton.click();

            WebElement deleteButton2 = driver.findElement(By.id("button2"));
            deleteButton2.click();

            List<WebElement> listOfElems = driver.findElements(By.id("item2"));
            // find all elements with li tag
            List<WebElement> totalElemsli = driver.findElements(By.xpath("//span[@id='content']/ul//li"));
            WebElement li1 = driver.findElement(By.id("item1"));
            // We use startsWith because getText includes the text of the Delete button
            assertEquals(totalElemsli.size(), 1);
            assertEquals(listOfElems.size(), 0);
            assertTrue(li1.getText().startsWith("Something to do"), "Checking correct text for added element");

            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

    @Test
    // test for adding new item after deleting first item out of two
    // item 2 should get invisible
    // item 1 should remain visible
    // item 3 should get visible after addition
    // if minus clicked, plus should become visible making minus invisible and vice-versa
    // if minus clicked, input field should also become invisible
    public void testAddItemAfterDeletionOfFirstItemOutOfTwo() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the form to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();
            input.clear();
            input.sendKeys("New item added");
            addButton.click();

            WebElement deleteButton1 = driver.findElement(By.id("button1"));
            deleteButton1.click();

            input.clear();
            input.sendKeys("Third item added");
            addButton.click();

            List<WebElement> listOfElems = driver.findElements(By.id("item1"));
            // find all elements with li tag
            List<WebElement> totalElemsli = driver.findElements(By.xpath("//span[@id='content']/ul//li"));

            WebElement li1 = driver.findElement(By.id("item2"));
            WebElement li3 = driver.findElement(By.id("item3"));
            // We use startsWith because getText includes the text of the Delete button
            assertEquals(listOfElems.size(), 0);
            assertEquals(totalElemsli.size(), 2);
            assertTrue(li1.getText().startsWith("New item added"), "Checking correct text for added element");
            assertTrue(li3.getText().startsWith("Third item added"), "Checking correct text for added element");

            WebElement minus = driver.findElement(By.id("controls1minus"));
            assertTrue(minus.isDisplayed());
            assertFalse(elt.isDisplayed());
            assertTrue(input.isDisplayed());

            minus.click();
            assertFalse(minus.isDisplayed());
            assertTrue(elt.isDisplayed());
            assertFalse(input.isDisplayed());
        } finally {
            driver.quit();
        }
    }

}
