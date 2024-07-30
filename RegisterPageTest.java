import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import pageobject.RegisterPage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.io.File;

public class RegisterPageTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    private Properties properties;

    @BeforeEach
    public void setUp() {
        properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/application.properties")) { // Use "/application.properties"
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set up ChromeDriverService with verbose logging
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("D:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe")) // Specify the path to your chromedriver executable
                .usingAnyFreePort()
                .withVerbose(true)
                .build();

        driver = new ChromeDriver(service);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(30));


        // Load page with retry mechanism
        loadPageWithRetry(properties.getProperty("baseURL"), 3);

        // Sending driver to the Register Page
        registerPage = new RegisterPage(driver);
    }

    private void loadPageWithRetry(String url, int maxRetries) {
        int retries = 0;
        while (retries < maxRetries) {
            try {
                driver.get(url);
                return;
            } catch (Exception e) {
                retries++;
                if (retries == maxRetries) {
                    throw new RuntimeException("Failed to load page after " + maxRetries + " attempts", e);
                }
            }
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void Register() {
        WebElement registerLinkedText = driver.findElement(registerPage.registerLinkedText);
        registerLinkedText.click();

       registerPage.windowSwitching();

        String expectedURL = "file:///D:/CraftCornerProject/SPPM/CraftCorner%20Code/register.html";
        String actualURL = driver.getCurrentUrl();
        Assertions.assertTrue(expectedURL.equals(actualURL), "Expected URL is not equal to Actual URL.");

        // Register Page

        WebElement username = driver.findElement(registerPage.registerUsernameLocator);
        username.sendKeys(properties.getProperty("correctUsername"));
        WebElement password = driver.findElement(registerPage.registerPasswordLocator);
        password.sendKeys(properties.getProperty("correctPassword"));
        WebElement confirmPassword = driver.findElement(registerPage.registerConfirmPasswordLocator);
        confirmPassword.sendKeys(properties.getProperty("correctPassword"));
        WebElement registerButton = driver.findElement(registerPage.registerButton);
        registerButton.click();

//        // Login Page
//
//        WebElement loginPage = driver.findElement(registerPage.loginButton);
//        loginPage.click();
//        WebElement loginUsername = driver.findElement(registerPage.loginUsernameLocator);
//        loginUsername.sendKeys(properties.getProperty("correctUsername"));
//        WebElement loginPassword = driver.findElement(registerPage.loginPasswordLocator);
//        loginPassword.sendKeys(properties.getProperty("correctPassword"));
//
//        // Login Page
//
//        WebElement loginButton = driver.findElement(registerPage.loginButtonLocator);
//        loginButton.click();
//
//        registerPage.windowSwitching();
//        String expectedURL1 = "file:///D:/CraftCornerProject/SPPM/CraftCorner%20Code/homepage.html?";
//        String actualURL1 = driver.getCurrentUrl();
//        Assertions.assertTrue(expectedURL1.equals(actualURL1), "Expected URL is not equal to Current URL.");
//
//        driver.close();
//    }

    }

//    @Test
//    public void LoginFunctionality(){
//
//        // Login Page
//
////        WebElement loginPage = driver.findElement(registerPage.loginButton);
////        loginPage.click();
//
//        WebElement loginUsername = driver.findElement(registerPage.loginUsernameLocator);
//        loginUsername.sendKeys(properties.getProperty("correctUsername"));
//        WebElement loginPassword = driver.findElement(registerPage.loginPasswordLocator);
//        loginPassword.sendKeys(properties.getProperty("correctPassword"));
//
//        // Login Page
//
//        WebElement loginButton = driver.findElement(registerPage.loginButtonLocator);
//        loginButton.click();
//
//        registerPage.windowSwitching();
//        String expectedURL1 = "file:///D:/CraftCornerProject/SPPM/CraftCorner%20Code/homepage.html?";
//        String actualURL1 = driver.getCurrentUrl();
//        Assertions.assertTrue(expectedURL1.equals(actualURL1), "Expected URL is not equal to Current URL.");
////        driver.close();

    }


