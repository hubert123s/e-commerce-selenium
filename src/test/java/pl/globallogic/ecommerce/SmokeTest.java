package pl.globallogic.ecommerce;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.globallogic.type.SizeType;

import java.math.BigDecimal;
import java.util.List;

public class SmokeTest extends BaseTest {

    @Test
    public void shouldSuccessfulItemSearch() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        Assert.assertTrue(searchPage.isEqualToSearchQuery(query));
    }

    @Test
    public void shouldNotFoundResult() {
        landingPage.visit(host);
        landingPage.searchQuery(invalidQuery);
        Assert.assertTrue(searchPage.isNotFoundResultForYourSearch());
    }

    // Verify if the range of price is correctly filtering
    @Test
    public void shouldRangeOfPrice() {
        catalogPage.visit(dressesCatalogPageWebsite);
        List<Integer> allPrices = catalogPage.getAllPrices();
        catalogPage.changeSliderRange();
        int[] rangeOfPrice = catalogPage.getRangeOfPrice();
        List<Integer> filteredAllPrices = catalogPage.getAllPrices();
        List<Integer> expectedValue = catalogPage.filterInRange(allPrices, rangeOfPrice[0], rangeOfPrice[1]);
        Assert.assertEquals(filteredAllPrices, expectedValue);
    }


    @Test
    public void shouldSortByPrice() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        List<Integer> unsortedPrices = searchPage.getAllPrices();
        searchPage.sortByPriceAscending();
        List<Integer> sortedPricesByPage = searchPage.getAllPrices();
        List<Integer> expectedSortedPricesByPage = searchPage.sortedPrices(unsortedPrices);
        searchPage.scrollToPrice();
        Assert.assertEquals(expectedSortedPricesByPage, sortedPricesByPage);
    }

    // verify that sort by in stock shows real amount of items in stock (failure in dresses)
    @Test
    public void shouldFilteringProductsThatAreInStock() {
        catalogPage.visit(catalogPageWebsite);
        catalogPage.filteringProductsThatAreInStock();
        Assert.assertFalse(catalogPage.IsProductWithMessageOutOfStock());
    }

    //  verify that amount of items is equal to the amount in the information on the search page
    @Test
    public void shouldCompareAmountOfItems() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        int expectedValue = catalogPage.countProducts();
        int actualValue = catalogPage.extractAmountOfProductsFromHeading();
        Assert.assertEquals(actualValue, expectedValue);
    }

    //verify that product is no longer in stock is not available to add to cart
    @Test
    public void shouldIsNotAvailableToAddToCartProductThatIsNoLongerInStock() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        productDetailsPage.findNotAvailableSize();
        Assert.assertTrue(productDetailsPage.isNotAvailableInStock());
    }

    @Test
    public void shouldAddToCart() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        SizeType sizeType = productDetailsPage.findAvailableSize();
        productDetailsPage.selectSize(sizeType);
        productDetailsPage.addToCart();
        productDetailsPage.closeModalWindow();
        searchPage.clickToCart();
        Assert.assertTrue(productDetailsPage.isVisibleTotalPrice());
    }

    @Test
    public void shouldAddOneProductAndRemoveOneProductFromCart() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        SizeType sizeType = productDetailsPage.findAvailableSize();
        productDetailsPage.selectSize(sizeType);
        productDetailsPage.addToCart();
        productDetailsPage.closeModalWindow();
        searchPage.clickToCart();
        productDetailsPage.removeProduct();
        Assert.assertTrue(productDetailsPage.isEmptyCart());
    }
    @Test
    public void shouldAddTwoProductsAndRemoveOneProductFromCart() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        SizeType sizeType = productDetailsPage.findAvailableSize();
        productDetailsPage.selectSize(sizeType);
        productDetailsPage.addToCart();
        productDetailsPage.closeModalWindow();
        landingPage.clickTShirts();
        searchPage.clickToProductImageLink();
        SizeType sizeTypeForTshirts = productDetailsPage.findAvailableSize();
        productDetailsPage.selectSize(sizeTypeForTshirts);
        productDetailsPage.addToCart();
        productDetailsPage.closeModalWindow();
        searchPage.clickToCart();
        productDetailsPage.removeProduct();
        System.out.println(productDetailsPage.getAmountOfProduct());
        System.out.println(productDetailsPage.getAmountOfDifferentProduct());

        //Assert.assertTrue(productDetailsPage.isEmptyCart());
    }

    // check if can type greater amount than available
    @Test
    public void shouldShowMessageIfAddGreaterAmountThanAvailable() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        SizeType sizeType = productDetailsPage.findAvailableSize();
        productDetailsPage.selectSize(sizeType);
        productDetailsPage.typeMoreQuantityThanAvailable();
        productDetailsPage.addToCart();
        Assert.assertTrue(productDetailsPage.isVisibleIsNotEnoughProductInStockMessage());
    }

    // check successful compare
    @Test
    public void shouldCompareTwoProducts() {
        catalogPage.visit(catalogPageWebsite);
        catalogPage.compareTwoProducts();
        Assert.assertTrue(catalogPage.areTwoProductsToCompare());
    }

    //verify that total cost consist of total products cost and total shipping
    @Test
    public void shouldSumTotalCost() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        SizeType sizeType = productDetailsPage.findAvailableSize();
        productDetailsPage.selectSize(sizeType);
        productDetailsPage.addToCart();
        productDetailsPage.closeModalWindow();
        searchPage.clickToCart();
        BigDecimal expectedValue = productDetailsPage.getProductsPrice().add(productDetailsPage.getShippingPrice());
        BigDecimal actualValue = productDetailsPage.getTotalPrice();
        Assert.assertEquals(actualValue, expectedValue);
    }
    @Test
    public void shouldProduct(){
        landingPage.visit(host);
        landingPage.clickTShirts();
        searchPage.clickToProductImageLink();
        Assert.assertTrue(productDetailsPage.isInAppropriateTab());
    }
    @Test
    public void shouldBeEmptyCartFromBeginning() {
        landingPage.visit(host);
        landingPage.clickToCart();
        Assert.assertTrue(productDetailsPage.isEmptyCart());
    }
    // verify viewed products working correctly
    @Test
    public void shouldShowViewedProduct() {
        landingPage.visit(host);
        landingPage.searchQuery(query);
        searchPage.clickToProductImageLink();
        String expectedName = productDetailsPage.getNameOfProduct();
        landingPage.searchQuery(query);
        String actualName = searchPage.getFirstViewedProduct();
        Assert.assertEquals(actualName, expectedName);
    }

    @Test
    public void shouldEnterValidEmailAddress() {
        landingPage.visit(host);
        landingPage.completeNewsletter();
        Assert.assertTrue(landingPage.isVisibleAlertSuccess());
    }

    @Test(dataProvider = "invalid-emails")
    public void shouldEnterInvalidEmailAddress(String invalidEmail) {
        landingPage.visit(host);
        landingPage.completeNewsletter(invalidEmail);
        Assert.assertTrue(landingPage.isVisibleAlertDanger());
    }

    // verify email address that is already in the store's database
    @Test
    public void shouldEnterValidEmailAddressTwice() {
        landingPage.visit(host);
        landingPage.typeSameEmailAddressTwice();
        Assert.assertTrue(landingPage.isVisibleAlertDanger());
    }

    //  verify that the regulations exist
    @Test
    public void shouldRegulationsExist() {
        landingPage.visit(host);
        landingPage.clickToRegulations();
        Assert.assertFalse(landingPage.isVisibleAlertDangerDiv());
    }

    // verify return to home Button
    @Test
    public void shouldReturnToHome() {
        landingPage.visit(host);
        landingPage.clickToRegulations();
        landingPage.clickToReturnToHome();
        Assert.assertTrue(landingPage.isHomePage());
    }

    @Test
    public void shouldCreateAccount() {
        landingPage.visit(host);
        landingPage.clickSignIn();
        authenticationPage.createAccountAndRegister();
        Assert.assertTrue(authenticationPage.isCreatedAccount());
    }

    //verify that social media links redirects to the target page
    @Test(dataProvider = "social-media-links")
    public void shouldSocialMediaLinkRedirectsToTargetPage(String locator, String expectedLink) {
        By transformedLocator = By.cssSelector(locator);
        landingPage.visit(host);
        landingPage.redirectToSocialLink(transformedLocator);
        Assert.assertTrue(landingPage.isRedirected(expectedLink));
    }

    @Test
    public void shouldReceiveEmail() {
        mailReceiverSystemPage.visit(temporaryMailHost);
        mailReceiverSystemPage.acceptPersonalData();

        String email = mailReceiverSystemPage.getEmailAddress();
        landingPage.visit(host);
        landingPage.clickContactUs();
        customerServicePage.completeForm(email);
        Assert.assertTrue(customerServicePage.hasBeenSent());
        mailReceiverSystemPage.visit(temporaryMailHost);
        mailReceiverSystemPage.pause(20);
       // mailReceiverSystemPage.visit(mailReceiverSystemPage.getLinkToEmailMessage());
        //mailReceiverSystemPage.checkMailFromShop();
        Assert.assertTrue(mailReceiverSystemPage.isDelivered());
    }

}
