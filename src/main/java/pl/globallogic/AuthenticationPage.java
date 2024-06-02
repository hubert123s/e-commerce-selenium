package pl.globallogic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthenticationPage extends BasePage{
    protected final By  emailAddressFieldLocator = By.xpath("//div[@class='form-group']//input[@type='text' and @id='email_create']");
    protected final By createAccountButtonLocator = By.cssSelector("button.btn.btn-default.button.button-medium.exclusive");
    protected final By misterRadioButtonLocator= By.id("id_gender1");
    protected final By firstNameFieldLocator =By.id("customer_firstname");
    protected final By lastNameFieldLocator = By.id("customer_lastname");
    protected final By passwordFieldLocator = By.id("passwd");
    protected final By registerAccountButtonLocator =By.id("submitAccount");
    protected final String firstName="John";
    protected final String lastName="Doe";
    protected final String password ="12345";

    public AuthenticationPage(WebDriver driver) {
        super(driver);
    }

    public void createAccount(String email){
        type(emailAddressFieldLocator,email);
        click(createAccountButtonLocator);
    }
    public void createAccountAndRegister(){
        createAccount(generateRandomEmail(10));
        findElementBy(misterRadioButtonLocator).click();
        type(firstNameFieldLocator,firstName);
        type(lastNameFieldLocator,lastName);
        type(passwordFieldLocator,password);
        click(registerAccountButtonLocator);
    }
    public boolean isCreatedAccount(){
        return isDisplayed(alertSuccessLocator);
    }
}
