package pl.globallogic;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final By productNameLocator = By.xpath("//ul[@class='product_list grid row']//li//h5[@itemprop='name']//a[@class='product-name']");
    protected final By productPricesLocator = By.xpath("//div[@class='right-block']//div[@class='content_price']//span[@class='price product-price']");
    protected final By alertSuccessLocator = By.cssSelector("p.alert.alert-success");
    protected final By alertDangerLocator = By.cssSelector("p.alert.alert-danger");
    protected final By alertDangerDIVLocator = By.cssSelector("div.alert.alert-danger");
    protected int timeout = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void visit(String url) {
        logger.info("Navigating to '{}'", url);
        driver.get(url);
    }

    protected WebElement findElementBy(By locator) {
        logger.info("Looking for element with locator {}", locator);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }

    protected List<WebElement> findElementsBy(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
        );
    }

    protected void type(By locator, String text) {
        logger.info("Typing '{}' to element located by {}", text, locator);
        WebElement target = findElementBy(locator);
        target.clear();
        target.sendKeys(text);
    }

    protected void click(By locator) {
        findElementBy(locator).click();
    }

    public void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted while pausing!!!");
            throw new RuntimeException(e);
        }
    }

    protected boolean isDisplayed(By locator) {
        logger.info("Verify visibility of element located by {}", locator);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException ex) {
            logger.warn("Timeout of {} wait for {}", timeout, locator);
            return false;
        }
        return true;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public int extractNumberFromText(String text) {
        String[] words = text.split(" ");

        // Iterate through words to find the number
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            if (word.matches("\\d+")) {
                stringBuilder.append(word);
            }
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public List<Integer> getAllPrices() {
        List<Integer> prices = new ArrayList<>();
        for (WebElement element : findElementsBy(productPricesLocator)) {
            System.out.println("tekst" + element.getText());
            String value = element.getText().replace("$", "").trim();
            prices.add(Integer.valueOf(value));
        }
        return prices;
    }

    public void scrollTo(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static String generateRandomEmail(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.random(length, allowedChars);
        email = temp.substring(0, temp.length() - 1) + "@testdata.com";
        return email;
    }
}
