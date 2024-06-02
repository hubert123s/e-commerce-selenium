package pl.globallogic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class CatalogPage extends BasePage {
    private final String host;
    protected final By availabilityInStockLocator = By.xpath("//label[@for='layered_quantity_1']//a[contains(text(), 'In stock')]");
    protected final By outOfStockMessageLocator = By.cssSelector("span.out-of-stock");
    protected final By addToCompareButton = By.cssSelector("a.add_to_compare");
    protected final By compareButtonLocator = By.cssSelector("button.btn.btn-default.button.button-medium.bt_compare.bt_compare");
    protected final By productImageProductComparisonLocator = By.cssSelector("a.product_image");
    protected final By headingCounterProduct = By.cssSelector("span.heading-counter");
    protected final By priceRangeLocator = By.id("layered_price_range");
    protected final By rangeOfPriceEnabledFiltersLocator = By.cssSelector("span.layered_subtitle");

    public CatalogPage(WebDriver driver, String host) {
        super(driver);
        this.host = host;
    }

    public void filteringProductsThatAreInStock() {
        click(availabilityInStockLocator);
    }

    public boolean IsProductWithMessageOutOfStock() {
        return isDisplayed(outOfStockMessageLocator);
    }

    public void compareTwoProducts() {
        findElementsBy(addToCompareButton).get(0).click();
        pause(1);
        findElementsBy(addToCompareButton).get(1).click();
        click(compareButtonLocator);
    }

    public boolean areTwoProductsToCompare() {
        return findElementsBy(productImageProductComparisonLocator).size() == 2;
    }

    public int countProducts() {
        return findElementsBy(productNameLocator).size();
    }

    public int extractAmountOfProductsFromHeading() {
        return extractNumberFromText(findElementBy(headingCounterProduct).getText());
    }

    public void changeSliderRange() {
        WebElement slider = findElementBy(priceRangeLocator);
        Actions actions = new Actions(driver);
        int desiredPositionX = 200;
        actions.moveToElement(slider, desiredPositionX, 0).build().perform();
        actions.clickAndHold(slider).build().perform();
        actions.release(slider).build().perform();
        findElementBy(rangeOfPriceEnabledFiltersLocator);
    }

    public int[] getRangeOfPrice() {
        String text = findElementBy(priceRangeLocator).getText();
        String[] prices = text.split("-");
        return new int[]{extractPriceWithoutDolar(prices[0]), extractPriceWithoutDolar(prices[1])};
    }

    private int extractPriceWithoutDolar(String text) {
        return Integer.parseInt(text.replace("$", "").trim());
    }

    public List<Integer> filterInRange(List<Integer> numbers, int min, int max) {
        return numbers.stream()
                .filter(n -> n >= min && n <= max)
                .toList();
    }
}