package page.android;

import annotation.Platform;
import element.MobElement;

@Platform("ANDROID")
public class AndroidHomePage {
    private MobElement  productItem = new MobElement("//android.widget.TextView[@content-desc='test-Item title' and @text='%s']");
    private MobElement addToCartButton = new MobElement("//android.view.ViewGroup[@content-desc='test-ADD TO CART' and ../..//android.widget.TextView[@text='%s']]");
    private MobElement cartIcon = new MobElement("//android.view.ViewGroup[@content-desc='test-Cart']");

    public void selectProduct(String productName) {
        productItem.createDynamicLocator(productName);
        productItem.click();
    }

    public void addToCart(String productName) {
        addToCartButton.createDynamicLocator(productName);
        addToCartButton.click();
    }

    public void goToCart() {
        cartIcon.click();
    }
}
