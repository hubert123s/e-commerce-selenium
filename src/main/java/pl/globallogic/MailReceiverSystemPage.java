package pl.globallogic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//https://tempail.com/
public class MailReceiverSystemPage extends BasePage {
    private final String host;
    protected final By consentPersonalData = By.cssSelector("p.fc-button-label");
    protected final By addressEmailLocator = By.id("eposta_adres");
    //protected final By shopMailLocator = By.cssSelector("//li[@class='mail ']//div[contains(text(), '[My Shop]')]");
    protected final By shopMailLocator = By.cssSelector("//a[contains(div[@class='baslik'], '[My Shop]')]");


    public MailReceiverSystemPage(WebDriver driver, String host) {
        super(driver);
        this.host = host;
    }

    public void acceptPersonalData() {
        click(consentPersonalData);
    }

    public String getEmailAddress() {
        return findElementBy(addressEmailLocator).getAttribute("value");
    }

    public void checkMailFromShop() {
        click(shopMailLocator);
    }

    public boolean isDelivered() {
        return findElementBy(shopMailLocator).isEnabled();
    }

    public String getLinkToEmailMessage() {
        String id = findElementBy(shopMailLocator).getAttribute("id");
        return "https://tempail.com/en/" + id;
    }
}
