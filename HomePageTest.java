import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.HomePage;
import pageobject.ShopPage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class HomePageTest {
    private WebDriver driver;
    private Properties properties;
    private HomePage homePage;
    private ShopPage shopPage;

    @BeforeEach
    public void setUp(){
        properties = new Properties();
        InputStream ls = getClass().getResourceAsStream("/application.properties");
        try {
            properties.load(ls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(properties.getProperty("homePageURL"));


        // Sending driver to Home Page
        homePage = new HomePage(driver, shopPage);
    }

    @AfterEach
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    @Test
    public void HomeButtonFunctionality(){
        WebElement homeButton = driver.findElement(homePage.homeButton);
        homeButton.click();

        String originalWindow = driver.getWindowHandle();
        for(String windowHandle: driver.getWindowHandles()){
            if(!originalWindow.equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String expectedURL = properties.getProperty("homePageURL");
        String actualURL = driver.getCurrentUrl();
        Assertions.assertEquals(expectedURL, actualURL, "Expected URL is not equal tp actual URL.");

    }

//    @Test
//    public void shopToHomePage(){
//        driver.get(properties.getProperty("homePageURL"));
//        WebElement shopButton = driver.findElement(shopPage.shopButton);
//        shopButton.click();
//       String expectedURLs =  properties.getProperty("shopPageURL");
//       String actualURLs = driver.getCurrentUrl();
//       Assertions.assertEquals(expectedURLs, actualURLs, "Test Failed...");
//
//        WebElement homeButton = driver.findElement(homePage.homeButton);
//        homeButton.click();
//
//        String originalWindow = driver.getWindowHandle();
//        for(String windowHandle: driver.getWindowHandles()){
//            if(!originalWindow.equals(windowHandle)){
//                driver.switchTo().window(windowHandle);
//                break;
//            }
//        }
//
//        String expectedURL = properties.getProperty("homePageURL");
//        String actualURL = driver.getCurrentUrl();
//        Assertions.assertEquals(expectedURL, actualURL, "Expected URL is not equal tp actual URL.");
//
//
//    }
}
