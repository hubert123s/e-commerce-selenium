package pl.globallogic.ecommerce;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import pl.globallogic.*;

import java.io.File;
import java.io.IOException;


public class BaseTest {
    protected WebDriver driver;
    protected String host = "http://www.automationpractice.pl/index.php";
    protected final String catalogPageWebsite = "http://www.automationpractice.pl/index.php?id_category=3&controller=category";
    protected final String dressesCatalogPageWebsite = "http://www.automationpractice.pl/index.php?id_category=8&controller=category";
    protected final String temporaryMailHost = "https://tempail.com/";
    protected String query = "dress";
    protected String invalidQuery = "d";
    protected LandingPage landingPage;
    protected SearchPage searchPage;
    protected CatalogPage catalogPage;
    protected ProductDetailsPage productDetailsPage;
    protected AuthenticationPage authenticationPage;
    protected MailReceiverSystemPage mailReceiverSystemPage;
    protected CustomerServicePage customerServicePage;
    @BeforeMethod
    public void testSetup() {
        System.out.println(System.getProperty("browser"));
        driver = new ChromeDriver();
        searchPage = new SearchPage(driver);
        landingPage = new LandingPage(driver, host);
        catalogPage = new CatalogPage(driver, catalogPageWebsite);
        productDetailsPage = new ProductDetailsPage(driver);
        authenticationPage = new AuthenticationPage(driver);
        mailReceiverSystemPage = new MailReceiverSystemPage(driver, temporaryMailHost);
        customerServicePage= new CustomerServicePage(driver);
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(driver, result.getName());
            System.out.println("Screenshot captured: " + screenshotPath);
        }
    }

    public static String captureScreenshot(WebDriver driver, String testName) throws IOException {
        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalArgumentException("Driver is not a TakesScreenshot");
        }
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        String fileName = testName + "_" + System.currentTimeMillis() + ".png";
        String destinationPath = "screenshots/" + fileName;
        FileUtils.copyFile(screenshotFile, new File(destinationPath));
        return destinationPath;
    }

    @AfterMethod(dependsOnMethods = "takeScreenshotOnFailure")
    public void cleanUp() {
        driver.quit();
    }

    @DataProvider(name = "social-media-links")
    public Object[][] data() {
        return new Object[][]{
                {"li.facebook", "facebook.com/prestashop"},
                {"li.twitter", "x.com/prestashop"}
        };
    }
    @DataProvider(name = "invalid-emails")
    public Object[][] invalidEmails() {
        return new Object[][]{
                {"usernameemail.com"},
                {"username@emailcom"},
                {"@email.com"},
                {"@emailcom"}
        };
    }
}
