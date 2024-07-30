import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.LoginPage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class LoginPageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private Properties properties;

    @BeforeEach
    public void setUp() throws IOException {
        properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/application.properties")) { // Use "/application.properties"
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(properties.getProperty("baseURL"));
//        "file:///D:/CraftCornerProject/SPPM/CraftCorner%20Code/login.html"

        // Sending driver to the Login Page
         loginPage = new LoginPage(driver);
    }

    @AfterEach

   public void tearDown() {
        if(driver != null){
            driver.quit();
        }
    }


@Test
    public void LoginFunctionality(){
        WebElement usernameLocator = driver.findElement(loginPage.loginUsernameLocator);
        usernameLocator.sendKeys(properties.getProperty("correctUsername"));
        WebElement passwordLocator = driver.findElement(loginPage.loginPasswordLocator);
        passwordLocator.sendKeys(properties.getProperty("correctPassword"));
        WebElement loginButton = driver.findElement(loginPage.loginButtonLocator);
        loginButton.click();

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

    driver.switchTo().window(originalWindow);

    }
}
