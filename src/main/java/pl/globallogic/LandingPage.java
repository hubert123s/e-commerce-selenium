package pl.globallogic;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends BasePage {
    private final String host;
    protected final By searchBarLocator = By.id("search_query_top");
    protected final By searchButtonLocator = By.cssSelector("button.btn.btn-default.button-search");
    protected final By newsletterFieldLocator = By.id("newsletter-input");
    protected final By newsletterButtonLocator = By.cssSelector("button.btn.btn-default.button.button-small");
    protected final String successFullySubscribedNewsletterMessage =" Newsletter : You have successfully subscribed to this newsletter.";
    protected final String emailIsAlreadyRegisteredMessage = "Newsletter : This email address is already registered.";
    protected final String invalidEmailAddressMessage= "Newsletter : This email address is already registered.";
    protected final By termsAndConditionsOfUseLocator = By.linkText("Terms and conditions of use");
    protected final By returnToHomeButtonLocator = By.cssSelector("i.icon-home");
    protected final By signInButtonLocator = By.cssSelector("a.login");
    protected final By contactUsButtonLocator = By.xpath("//div[@id='contact-link']/a[text()='Contact us']");
    private static final int LENGTH_OF_EMAIL = 7;


    public LandingPage(WebDriver driver, String host) {
        super(driver);
        this.host = host;
    }

    public void searchQuery(String query) {
        type(searchBarLocator, query);
        click(searchButtonLocator);
    }

    public void completeNewsletter() {
        type(newsletterFieldLocator, generateRandomEmail(LENGTH_OF_EMAIL));
        click(newsletterButtonLocator);
    }
    public void completeNewsletter(String generatedEmail) {
        type(newsletterFieldLocator, generatedEmail);
        click(newsletterButtonLocator);
    }

    public boolean isVisibleAlertSuccess() {
        System.out.println(findElementBy(alertSuccessLocator).getText());
        return isDisplayed(alertSuccessLocator);
    }
    public void typeSameEmailAddressTwice(){
        String generatedEmail = generateRandomEmail(LENGTH_OF_EMAIL);
        completeNewsletter(generatedEmail);
        driver.navigate().back();
        completeNewsletter(generatedEmail);
    }
    public boolean isVisibleAlertDanger() {
        System.out.println(findElementBy(alertDangerLocator).getText());
        return isDisplayed(alertDangerLocator);
    }
    public boolean isVisibleAlertDangerDiv() {
        return isDisplayed(alertDangerDIVLocator);
    }
    public void redirectToSocialLink(By locator){
        click(locator);
    }
    public boolean isRedirected(String expectedURL) {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            String actualURL = driver.getCurrentUrl();
            if (actualURL.contains(expectedURL)) {
                return true;
            }
        }
        return false;
    }
    public void clickToRegulations(){
        click(termsAndConditionsOfUseLocator);
    }
    public void clickToReturnToHome(){
        click(returnToHomeButtonLocator);
    }
    public boolean isHomePage(){
        return driver.getCurrentUrl().equals(host);
    }
    public void clickSignIn(){
        click(signInButtonLocator);
    }
    public void clickContactUs(){
        click(contactUsButtonLocator);
    }

}
