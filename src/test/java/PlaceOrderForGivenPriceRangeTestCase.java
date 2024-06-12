import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectItemWithInGivenPriceRange {


    private WebDriver driver;

    @BeforeEach
    public void setUp(){
        /*
           As part of setup, initialize WebDriver with Firefox executables.
           Please note that this executable is compatible with Mac OS.
           Please download from browser for Windows OS if needed.
        */
        File pathToBinary = new File("geckodriver.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);
        DesiredCapabilities desired = new DesiredCapabilities();
        FirefoxOptions options = new FirefoxOptions();
        desired.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(firefoxBinary));
         /*
            Provide website, user name and password to driver and click login button.
         */
        driver = new FirefoxDriver(options);
        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        username.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

    @Test
    public void addItemAndCheckout(){

        /*
            Initialize String arrays for item ids and item price.
            Initialize minPrice and maxPrice variable and totalPrice to keep
            track of price of items added in shopping cart.
         */
        double totalPrice = 0;
        double minPrice = 30;
        double maxPrice = 60;
        String[] itemIds = {"add-to-cart-sauce-labs-backpack", "add-to-cart-sauce-labs-backpack", "add-to-cart-sauce-labs-bolt-t-shirt", "add-to-cart-sauce-labs-fleece-jacket"
                "add-to-cart-sauce-labs-onesie", "add-to-cart-test.allthethings()-t-shirt-(red)"};
        String[] itemPrice = {"$29.99", "$9.99", "$15.99", "$49.99", "$7.99", "$15.99"};

        /*
            Iterate through itemIds String array and compare price of item once added to totalPrice agains
            maxPrice.
            If total is less than or equal to maxPrice, add to shopping cart but clicking on addButton.
            Simultaneously increment the totalPrice attribute by price of item added.
            If totalPrice becomes greater than or equal to minPrice and less than or equal to maxPrice,
            come out of iteration.
         */
        for(int i = 0; i < itemIds.length; i++){
            if(totalPrice + Double.parseDouble(itemPrice[i].replace("$", "")) <= maxPrice){
                WebElement addButton = driver.findElement(By.id(itemIds[i]));
                addButton.click();
                totalPrice += Double.parseDouble(itemPrice[i].replace("$", ""));
            }

            if(totalPrice >= minPrice && totalPrice <= maxPrice){
                break;
            }
        }

        if(totalPrice < minPrice || totalPrice > maxPrice){
            return;
        }

        // Click on cart button
        WebElement cartButton = driver.findElement(By.id("shopping_cart_link"));
        cartButton.click();

        // Click on check out button
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();

        /*
            Fill out form details for checkout -
            first name
            last name
            postal code
         */
        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("Adam");

        WebElement secondName = driver.findElement(By.id("last-name"));
        secondName.sendKeys("Smith");

        WebElement postalCode = driver.findElement(By.id("postal-code"));
        postalCode.sendKeys("30009");

        // Click on continue button
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        // Click on finish button
        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();

        // Check if complete header is displayed or not
        WebElement completeHeader = driver.findElement(By.className("complete-header"));
        assertTrue(completeHeader.isDisplayed());

    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
