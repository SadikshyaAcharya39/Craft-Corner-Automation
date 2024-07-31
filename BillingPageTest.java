import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.BillingPage;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class BillingPageTest {
    private WebDriver driver;
    private Properties properties;
    private BillingPage billingPage;

    @BeforeEach
    public void setUp(){

        properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/application.properties")) { // Use "/application.properties"
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Launch The Browser
        driver = new ChromeDriver();

        // Window Maximize
        driver.manage().window().maximize();

        // Implicitly Wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Hit the URL
//        driver.get(properties.getProperty("baseURL"));
        driver.get(properties.getProperty("checkOutURL"));

        // Send the driver
        billingPage = new BillingPage(driver);

    }

    @AfterEach

    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test
    public void BillingDetails(){
        billingPage.billingPageFunctionality();

        WebElement checkOutButton = driver.findElement(billingPage.checkOutButtonLocator);
        Assertions.assertEquals("Check Out", checkOutButton.getText().trim(), "Test Failed...");
        checkOutButton.click();
        billingPage.driverWait();
    }

    @Test
    public void checkOutFunctionality(){

        billingPage.billingPageFunctionality();

        WebElement checkOutEsewa = driver.findElement(billingPage.checkFieldEsewaLocator);
        Assertions.assertTrue(checkOutEsewa.isDisplayed(), "Test Failed...");
        checkOutEsewa.click();

        WebElement checkOutButton = driver.findElement(billingPage.checkOutButtonLocator);
        Assertions.assertEquals("Check Out", checkOutButton.getText().trim(), "Test Failed...");
        checkOutButton.click();
        billingPage.driverWait();
    }

    // Failed Test

    @Test
    public void afterCheckOutFunctionality(){
        checkOutFunctionality();
        String expectedURL = properties.getProperty("homePageURL");
        String actualURL = driver.getCurrentUrl();
        Assertions.assertEquals(expectedURL, actualURL, " ExpectedURL is not equal to ActualURL. ");
    }

    @Test
    public void atLeastOneCheckboxShouldBeClicked(){
        billingPage.billingPageFunctionality();

        WebElement checkOutButton = driver.findElement(billingPage.checkOutButtonLocator);
        Assertions.assertEquals("Check Out", checkOutButton.getText().trim(), "Test Failed...");
        checkOutButton.click();
        billingPage.driverWait();

        // Locate all the elements
        List<WebElement> checkBoxes = driver.findElements(billingPage.checkBoxLocator);

        boolean atLeastOneChecked = false;

        // Check if at least one checkbox is selected
        for(WebElement checkBox: checkBoxes) {
            if (checkBox.isSelected()) {
                atLeastOneChecked = true;
                break;
            }
        }
            // Assert that at least one checkBox is selected
            Assertions.assertTrue(atLeastOneChecked, "AtLeast one checkbox should be selected ");

        }


    @Test
    public void notAllCheckBoxShouldBeCheckedAtOnce(){
        billingPage.billingPageFunctionality();
        WebElement checkOutButton = driver.findElement(billingPage.checkOutButtonLocator);
        Assertions.assertEquals("Check Out", checkOutButton.getText().trim(), "Test Failed...");
        checkOutButton.click();
        billingPage.driverWait();

        // LOCATE ALL THE ELEMENTS
        List<WebElement> checkboxes = driver.findElements(billingPage.checkBoxLocator);

        // Check if all checkboxes are selected
        boolean allCheckedAtOnce = true;

        // Check If all checkboxes are selected
        for(WebElement checkbox: checkboxes){
            if(!checkbox.isSelected()) {
                allCheckedAtOnce = false;
                break;
            }
        }

        // Assert that at least one checkBox is selected
        Assertions.assertTrue(allCheckedAtOnce, "At Once all checkboxes should not be selected ");

    }

}
