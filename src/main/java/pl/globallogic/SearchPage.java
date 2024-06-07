package pl.globallogic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.globallogic.type.SortByType;

import java.util.List;

public class SearchPage extends BasePage {
    protected final By sortByButtonLocator = By.id("selectProductSort");
    protected final By notFoundResultMessage = By.cssSelector("p.alert.alert-warning");
    protected final By productImageLinkLocator = By.cssSelector("a.product_img_link");
    protected final By viewedProductLocator = By.xpath("//div[@id='viewed-products_block_left']//div[@class='product-content']//h5/a[@class='product-name']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    private WebElement findFirstElement() {
        return findElementsBy(productNameLocator).get(0);
    }

    public boolean isEqualToSearchQuery(String query) {
        return findFirstElement().getText().toLowerCase().contains(query.toLowerCase());
    }

    public boolean isNotFoundResultForYourSearch() {
        findElementBy(notFoundResultMessage).getText();
        return isDisplayed(notFoundResultMessage);
    }

    public void sortByPriceAscending() {
        findElementBy(sortByButtonLocator).sendKeys(SortByType.LOWEST_FIRST.toString());
    }

    public List<Integer> sortedPrices(List<Integer> prices) {
        return prices.stream()
                .sorted()
                .toList();
    }


    public void clickToProductImageLink() {
        click(productImageLinkLocator);
    }

    public String getFirstViewedProduct() {
        return findElementBy(viewedProductLocator).getText();
    }

    public void scrollToPrice() {
        scrollTo(productPricesLocator);
    }
}
