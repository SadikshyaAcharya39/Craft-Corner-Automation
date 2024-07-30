import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.LoginPage;
import pageobject.ShopPage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ShopPageTest {
    private WebDriver driver;
    private Properties properties;
    private ShopPage shopPage;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {

        properties = new Properties();
        InputStream ls = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(ls);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Send driver to the Login Page
        loginPage = new LoginPage(driver);

        // Send driver to the Shop Page
        shopPage = new ShopPage(driver, loginPage);
    }

    @AfterEach

    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
    @Test
    public void shopNowButtonFunctionality(){
        driver.get(properties.getProperty("homePageURL"));
        WebElement shopNowButton = driver.findElement(shopPage.shopNowButtonLocator);
        shopNowButton.click();

        String originalWindow = driver.getWindowHandle();
        for(String  windowHandle: driver.getWindowHandles()){
            if(!originalWindow.equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String expectedURL = properties.getProperty("shopPageURL");
        String actualURL = driver.getCurrentUrl();
        Assertions.assertEquals(expectedURL, actualURL, "Expected URL is not equal to actual URL. ");

        driver.switchTo().window(originalWindow);
        driver.close();
    }

    @Test
    public void ShopPage(){
        driver.get(properties.getProperty("homePageURL"));
        WebElement shopButton = driver.findElement(shopPage.shopButton);
        shopButton.click();

        String originalWindow = driver.getWindowHandle();
        for(String windowHandle: driver.getWindowHandles()){
            if(!originalWindow.equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String expectedURL = properties.getProperty("shopPageURL");
        String actualURL = driver.getCurrentUrl();
        Assertions.assertEquals(expectedURL, actualURL, "ExpectedURL and ActualURL does not match. ");

        driver.close();
//        driver.switchTo().window(originalWindow);
    }

    @Test
    public void addToCartFunctionality(){
        driver.get(properties.getProperty("homePageURL"));
        WebElement shopNowButton = driver.findElement(shopPage.shopNowButtonLocator);
        shopNowButton.click();

        WebElement addToCartButton = driver.findElement(shopPage.addToCartButtonLocator);
        addToCartButton.click();

        // Check Out Page

        String originalWindow = driver.getWindowHandle();
        for(String windowHandle: driver.getWindowHandles()){
            if(!originalWindow.equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String expectedURL = "file:///D:/CraftCornerProject/SPPM/CraftCorner%20Code/product.html?img=https://sumnimacollection.com/images/thumbs/0000134_bhadgaule-topi_415.jpeg&title=Bhat%20Gaule%20Topi";
        String actualURL = driver.getCurrentUrl();
        Assertions.assertEquals(expectedURL, actualURL, "Expected and Actual URLs are not same");

        WebElement buyNowButton = driver.findElement(shopPage.buyNowButtonLocator);
        buyNowButton.click();

        WebElement productPriceDetails = driver.findElement(shopPage.productPriceDetailsLocator);
        Assertions.assertTrue(productPriceDetails.isDisplayed(), " Product Price Details is displayed. ");

        // Check Out Button
        WebElement checkOutButton = driver.findElement(shopPage.checkOutButtonLocator);
        checkOutButton.click();

        driver.close();
        driver.switchTo().window(originalWindow);

//        WebElement billingDetails = driver.findElement(shopPage.);


    }


}
