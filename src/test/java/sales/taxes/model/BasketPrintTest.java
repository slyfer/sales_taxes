/**
 *
 */
package sales.taxes.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Basket}
 *
 * @author Ciro Cardone
 *
 */
public class BasketPrintTest {

    /**
     * Test input1
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testInput1() throws UnsupportedEncodingException, IOException {

        final BasketItem basketItem1 = new BasketItem(1, "book", 12.49, false, false);
        final BasketItem basketItem2 = new BasketItem(1, "music CD", 14.99, true, false);
        final BasketItem basketItem3 = new BasketItem(1, "chocolate bar", 0.85, false, false);

        final ArrayList<BasketItem> items = new ArrayList<BasketItem>();
        items.add(basketItem1);
        items.add(basketItem2);
        items.add(basketItem3);

        final String expectedPrint = "1 book: 12.49\n"

        + "1 music CD: 16.49\n"

        + "1 chocolate bar: 0.85\n"

        + "Sales Taxes: 1.50\n"

        + "Total: 29.83";

        verifyOutput(items, expectedPrint);

    }

    /**
     * Test input2
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testInput2() throws UnsupportedEncodingException, IOException {

        final BasketItem basketItem1 = new BasketItem(1, "box of chocolates", 10.00, false, true);
        final BasketItem basketItem2 = new BasketItem(1, "bottle of perfume", 47.50, true, true);

        final ArrayList<BasketItem> items = new ArrayList<BasketItem>();
        items.add(basketItem1);
        items.add(basketItem2);

        final String expectedPrint = "1 imported box of chocolates: 10.50\n"

        + "1 imported bottle of perfume: 54.65\n"

        + "Sales Taxes: 7.65\n"

        + "Total: 65.15";

        verifyOutput(items, expectedPrint);

    }

    /**
     * Test input3
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testInput3() throws UnsupportedEncodingException, IOException {

        final BasketItem basketItem1 = new BasketItem(1, "bottle of perfume", 27.99, true, true);
        final BasketItem basketItem2 = new BasketItem(1, "bottle of perfume", 18.99, true, false);
        final BasketItem basketItem3 = new BasketItem(1, "packet of headache pills", 9.75, false, false);
        final BasketItem basketItem4 = new BasketItem(1, "box of chocolates", 11.25, false, true);

        final ArrayList<BasketItem> items = new ArrayList<BasketItem>();
        items.add(basketItem1);
        items.add(basketItem2);
        items.add(basketItem3);
        items.add(basketItem4);

        final String expectedPrint = "1 imported bottle of perfume: 32.19\n"

        + "1 bottle of perfume: 20.89\n"

        + "1 packet of headache pills: 9.75\n"

        + "1 imported box of chocolates: 11.85\n"

        + "Sales Taxes: 6.70\n"

        + "Total: 74.68";

        verifyOutput(items, expectedPrint);

    }

    /**
     * Verifies that basket print a string equals to expectedPrint
     *
     * @param items
     * @param expectedPrint
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private void verifyOutput(final ArrayList<BasketItem> items, final String expectedPrint) throws UnsupportedEncodingException, IOException {
        final Basket basket = new Basket();

        for (final BasketItem basketItem : items) {
            basket.add(basketItem);
        }

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        basket.print(outputStream);

        Assert.assertEquals(expectedPrint, outputStream.toString("UTF-8"));
    }

}
