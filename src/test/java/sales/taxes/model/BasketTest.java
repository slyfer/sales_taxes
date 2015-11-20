/**
 *
 */
package sales.taxes.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Basket}
 *
 * @author Ciro Cardone
 *
 */
public class BasketTest {

    /**
     * Test empty basket
     */
    @Test
    public void testEmptyBasket() {

        final Basket basket = new Basket();

        Assert.assertEquals(0.0, basket.getSalesTaxes(), 0.001);
        Assert.assertEquals(0.0, basket.getTotal(), 0.001);
        Assert.assertEquals(0, basket.getNumberOfItems());

    }

    /**
     * Test item add
     */
    @Test
    public void testItemAddBasket() {

        final Basket basket = new Basket();

        final BasketItem basketItem1 = new BasketItem(1, "item1", 10.0, false, true);

        basket.add(basketItem1);

        final double item1Taxes = 0.50;
        final double item1Total = 10.50;

        Assert.assertEquals(item1Taxes, basket.getSalesTaxes(), 0.001);
        Assert.assertEquals(item1Total, basket.getTotal(), 0.001);
        Assert.assertEquals(1, basket.getNumberOfItems());

        final BasketItem basketItem2 = new BasketItem(2, "item2", 20.50, true, false);
        basket.add(basketItem2);

        final double item2Taxes = 2.05 * 2;
        final double item2Total = (2.05 * 2) + (20.50 * 2);

        Assert.assertEquals(item1Taxes + item2Taxes, basket.getSalesTaxes(), 0.001);
        Assert.assertEquals(item1Total + item2Total, basket.getTotal(), 0.001);
        Assert.assertEquals(3, basket.getNumberOfItems());

    }

    /**
     * Test clear
     */
    @Test
    public void testClear() {

        final Basket basket = new Basket();

        final BasketItem basketItem1 = new BasketItem(1, "item1", 10.0, false, true);
        basket.add(basketItem1);

        final BasketItem basketItem2 = new BasketItem(2, "item2", 20.50, true, false);
        basket.add(basketItem2);

        Assert.assertTrue(basket.getNumberOfItems() > 0);

        basket.clear();

        Assert.assertEquals(0.0, basket.getSalesTaxes(), 0.001);
        Assert.assertEquals(0.0, basket.getTotal(), 0.001);
        Assert.assertEquals(0, basket.getNumberOfItems());

    }

    /**
     * Test for {@link BasketItem#print(java.io.OutputStream)}
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testPrint() throws UnsupportedEncodingException, IOException {

        final Basket basket = new Basket();

        final BasketItem basketItem1 = new BasketItem(1, "item1", 100.0, false, false);
        basket.add(basketItem1);

        final BasketItem basketItem2 = new BasketItem(2, "item2", 20.50, true, false);
        basket.add(basketItem2);

        final BasketItem basketItem3 = new BasketItem(1, "item3", 50.0, true, true);
        basket.add(basketItem3);

        final BasketItem basketItem4 = new BasketItem(3, "item4", 8.20, false, true);
        basket.add(basketItem4);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        basket.print(outputStream);

        final String expectedPrint = "1 item1: 100.00\n"

        + "2 item2: 45.10\n"

        + "1 imported item3: 57.50\n"

        + "3 imported item4: 25.95\n"

        + "Sales Taxes: 12.95\n"

        + "Total: 228.55";

        Assert.assertEquals(expectedPrint, outputStream.toString("UTF-8"));
    }
}
