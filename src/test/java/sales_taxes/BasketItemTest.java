/**
 *
 */
package sales_taxes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

import sales.taxes.model.BasketItem;

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
        Assert.assertEquals(price, item.getPrice(), 0.0);
        Assert.assertEquals(taxable, item.isTaxable());
        Assert.assertEquals(imported, item.isImported());
        Assert.assertEquals(1, item.getQuantity());

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
        final int quantity = 1;

        // not imported
        {
            final boolean imported = false;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            item.print(outputStream);

            Assert.assertEquals("1 dummy: 11.00", outputStream.toString("UTF-8"));
        }

        // imported
        {
            final boolean imported = true;

            final BasketItem item = new BasketItem(quantity, name, price, taxable, imported);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            item.print(outputStream);

            Assert.assertEquals("1 imported dummy: 11.50", outputStream.toString("UTF-8"));
        }
    }
}
