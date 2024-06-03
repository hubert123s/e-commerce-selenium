package pl.globallogic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class CustomerServicePage extends BasePage{
    protected final By subjectHeadingLocator = By.id("id_contact");
    protected final By emailFieldLocator = By.id("email");
    protected final By submitMessageButtonLocator = By.id("submitMessage");
    protected final By messageFieldLocator = By.id("message");
    protected final By fileUploadLocator = By.id("fileUpload");
    protected final String fileName = "screenshots\\shouldSortByPrice_1717334113290.png";

    private final String text = "test";
    public CustomerServicePage(WebDriver driver) {
        super(driver);
    }
    public void completeForm(String email){
        String filePath = getRelativePath(fileName);
        Select select = new Select(findElementBy(subjectHeadingLocator));
        select.selectByVisibleText("Customer service");
        type(emailFieldLocator,email);
        type(messageFieldLocator,text);
        type(fileUploadLocator,filePath);
        click(submitMessageButtonLocator);
    }
    public String getRelativePath(String fileNameWithExtension) {
        System.out.println(new File(fileNameWithExtension).getAbsolutePath());
        return new File(fileNameWithExtension).getAbsolutePath();
    }
    public boolean hasBeenSent(){
        return isDisplayed(alertSuccessLocator);
    }
}
