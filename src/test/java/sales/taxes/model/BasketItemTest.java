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
 * Test class for {@link BasketItem}
 *
 * @author Ciro Cardone
 *
 */
public class BasketItemTest {

    /**
     * Verifies constructor, skipping taxes
     */
    @Test
    public void testConstructor() {
        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = false;
        final boolean imported = true;
        final int quantity = 1;

        final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

        Assert.assertEquals(name, item.getName());
        Assert.assertEquals(price, item.getPrice(), 0.001);
        Assert.assertEquals(taxable, item.isTaxable());
        Assert.assertEquals(imported, item.isImported());
        Assert.assertEquals(1, item.getQuantity());
    }

    /**
     * Verifies getPrice
     */
    @Test
    public void testGetPrice() {

        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = false;
        final boolean imported = true;

        // 1 item
        {
            final int quantity = 1;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedPrice = 10.0;
            Assert.assertEquals(expectedPrice, item.getPrice(), 0.001);
        }

        // 2 item
        {
            final int quantity = 2;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedPrice = 20.0;
            Assert.assertEquals(expectedPrice, item.getPrice(), 0.001);
        }
    }

    /**
     * Verifies getTaxes
     */
    @Test
    public void testGetTaxes() {

        final String name = "dummy";
        final double price = 10.0;

        // 1 item no taxable
        {
            final boolean taxable = false;
            final boolean imported = false;

            final int quantity = 1;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedTaxes = 0.0;
            Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
        }

        // 2 item no taxable
        {
            final int quantity = 2;

            final boolean taxable = false;
            final boolean imported = false;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedTaxes = 0.0;
            Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
        }

        // 1 item taxable
        {
            final boolean taxable = true;
            final boolean imported = false;

            final int quantity = 1;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedTaxes = 1.0;
            Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
        }

        // 2 item taxable
        {
            final boolean taxable = true;
            final boolean imported = false;

            final int quantity = 2;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedTaxes = 2.0;
            Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
        }

        // 1 item taxable imported
        {
            final boolean taxable = true;
            final boolean imported = true;

            final int quantity = 1;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedTaxes = 1.5;
            Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
        }

        // 2 item taxable imported
        {
            final boolean taxable = true;
            final boolean imported = true;

            final int quantity = 2;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final double expectedTaxes = 3.0;
            Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
        }

    }

    /**
     * Test for {@link BasketItem#print(java.io.OutputStream)}
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testPrint() throws UnsupportedEncodingException, IOException {
        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = true;

        // 1 not imported
        {
            final int quantity = 1;
            final boolean imported = false;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            item.print(outputStream);

            // 11.00 = 10.00 + 1.00 (sales taxes)
            Assert.assertEquals("1 dummy: 11.00", outputStream.toString("UTF-8"));
        }

        // 2 imported
        {
            final int quantity = 2;
            final boolean imported = true;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            item.print(outputStream);

            // 23.00 = 10.00 * 2 (price) + 1.00 * 2 (sales taxes) + 0.50 * 2
            // (imported taxes)
            Assert.assertEquals("2 imported dummy: 23.00", outputStream.toString("UTF-8"));
        }
    }
}
