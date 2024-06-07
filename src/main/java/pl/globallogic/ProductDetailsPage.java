package pl.globallogic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pl.globallogic.type.SizeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductDetailsPage extends BasePage {

    protected final By inStockValueLocator = By.cssSelector("span.label.label-success");
    protected final By noLongerInStockLocator = By.cssSelector("span.label.label-warning");
    protected final By selectorSizeOfProductLocator = By.id("group_1");
    protected final By addToCardButtonLocator = By.xpath("//div[@class='box-cart-bottom']//p[@id='add_to_cart']//button[@type='submit' and @name='Submit']");
    protected final By addToCardLocator = By.xpath("p.buttons_bottom_block.no-print");
    protected final By closeModalWindowButtonLocator = By.xpath("//span[@class='cross' and @title='Close window']");
    protected final By totalPriceLocator = By.id("total_price");
    protected final By totalShippingLocator = By.id("total_shipping");
    protected final By totalProductsLocator = By.id("total_product");
    protected final By quantityFieldLocator = By.id("quantity_wanted");
    protected final By iconPlusButtonLocator = By.cssSelector("a.btn.btn-default.button-plus.product_quantity_up span");
    protected final By quantityAvailableLocator = By.id("quantityAvailable");
    protected final By isNotEnoughProductInStockLocator = By.cssSelector("p.fancybox-error");
    protected final By nameOfProduct = By.xpath("//h1[@itemprop='name']");
    protected final By trashButton = By.cssSelector("a.cart_quantity_delete");
    protected final By productNameLocator = By.cssSelector("p.product-name");
    protected final By amountOfProductsLocator = By.id("summary_products_quantity");
    protected final By tshirtsTabNameLocator = By.xpath("//span[@itemprop='title' and text()='T-shirts']");
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }


    public SizeType findAvailableSize() {
        for (SizeType size : SizeType.values()) {
            selectSize(size);
            System.out.println(isAvailableInStock());
            if (isAvailableInStock()) return size;
        }
        return SizeType.S;
    }

    public SizeType findNotAvailableSize() {
        for (SizeType size : SizeType.values()) {
            selectSize(size);
            System.out.println(isNotAvailableInStock());
            if (isNotAvailableInStock()) return size;
        }
        return SizeType.S;
    }

    public void selectSize(SizeType size) {
        WebElement selectElement = findElementBy(selectorSizeOfProductLocator);
        Select select = new Select(selectElement);
        select.selectByValue(size.toString());
    }

    public boolean isAvailableInStock() {
        return isDisplayed(inStockValueLocator);
    }

    public boolean isNotAvailableInStock() {
        return isDisplayed(noLongerInStockLocator);
    }

    public void closeModalWindow() {
        pause(2);
        click(closeModalWindowButtonLocator);
    }

    public void addToCart() {
        click(addToCardButtonLocator);
    }

    public boolean isNotVisibleAddToCardButton() {
        String attribute = findElementBy(addToCardLocator).getAttribute("style");
        return attribute.equals("display: block;");
    }

    public boolean isVisibleTotalPrice() {
        return isDisplayed(totalPriceLocator);
    }

    public String getNameOfProduct() {
        return findElementBy(nameOfProduct).getText();
    }

    public BigDecimal getTotalPrice() {
        String value = getPriceWithOutDolar(totalPriceLocator);
        return new BigDecimal(value);
    }

    public BigDecimal getProductsPrice() {
        String value = getPriceWithOutDolar(totalProductsLocator);
        return new BigDecimal(value);
    }

    public BigDecimal getShippingPrice() {
        String value = getPriceWithOutDolar(totalShippingLocator);
        return new BigDecimal(value);
    }

    private String getPriceWithOutDolar(By priceLocator) {
        WebElement element = findElementBy(priceLocator);
        return element.getText().replace("$", "").trim();
    }

    public int getQuantity() {
        return Integer.parseInt(findElementBy(quantityFieldLocator).getAttribute("value"));
    }

    public int getQuantityAvailable() {
        return Integer.parseInt(findElementBy(quantityAvailableLocator).getText());
    }

    public void typeMoreQuantityThanAvailable() {
        type(quantityFieldLocator, String.valueOf(getQuantityAvailable() + 1));
    }

    public boolean isVisibleIsNotEnoughProductInStockMessage() {
        return isDisplayed(isNotEnoughProductInStockLocator);
    }
    public void removeProduct(){
        click(trashButton);
    }

    public int getAmountOfDifferentProduct() {
        return findElementsBy(productNameLocator).size();
    }

    public int getAmountOfProduct() {
        return Integer.parseInt(findElementBy(amountOfProductsLocator).getText().split("products")[0].trim());
    }
    public boolean isEmptyCart(){
        return isDisplayed(alertWarningLocator);
    }
    public List<String> divideNameOfProduct(){
        String [] words = findElementBy(nameOfProduct).getText().toLowerCase().split(" ");
        List<String> separetedWords= new ArrayList<>();
        for(String word:words){
            separetedWords.add(word.trim());
        }
        return separetedWords;
    }
    public String getTshirtsTabName(){
        return findElementBy(tshirtsTabNameLocator).getText().toLowerCase();
    }
    public boolean isInAppropriateTab(){
        return divideNameOfProduct().contains(getTshirtsTabName());
    }
}
