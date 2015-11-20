/**
 *
 */
package sales_taxes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

import sales.taxes.model.Item;

/**
 * Test class for {@link Item}
 *
 * @author Ciro Cardone
 *
 */
public class ItemTest {

    /**
     * Verifies constructor
     */
    @Test
    public void testConstructor() {
        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = false;
        final boolean imported = true;

        final Item item = new Item(name, price, taxable, imported);

        Assert.assertEquals(name, item.getName());
        Assert.assertEquals(price, item.getPrice(), 0.0);
        Assert.assertEquals(taxable, item.isTaxable());
        Assert.assertEquals(imported, item.isImported());

    }

    /**
     * Verifies that taxes are equal to 0 for a not taxable item (imported or
     * not)
     */
    @Test
    public void testNoTaxableItem() {

        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = false;

        // not imported
        // no tax applicable
        {
            final boolean imported = false;

            final Item item = new Item(name, price, taxable, imported);

            Assert.assertEquals(0.0, item.getTaxes(), 0.0);
        }

        // imported
        // taxes must be 0.5
        {
            final boolean imported = true;

            final Item item = new Item(name, price, taxable, imported);

            Assert.assertEquals(0.5, item.getTaxes(), 0.0);
        }
    }

    /**
     * Verifies that taxes are correctly computed
     */
    @Test
    public void testTaxableItem() {

        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = true;

        // not imported
        // taxes must be 1.0
        {
            final boolean imported = false;

            final Item item = new Item(name, price, taxable, imported);

            Assert.assertEquals(1.0, item.getTaxes(), 0.0);
        }

        // imported
        // taxes must be 1.5
        {
            final boolean imported = true;

            final Item item = new Item(name, price, taxable, imported);

            Assert.assertEquals(1.5, item.getTaxes(), 0.0);
        }

    }

    /**
     * Test for {@link Item#print(java.io.OutputStream)}
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testPrint() throws UnsupportedEncodingException, IOException {
        final String name = "dummy";
        final double price = 10.0;
        final boolean taxable = true;

        // not imported
        {
            final boolean imported = false;

            final Item item = new Item(name, price, taxable, imported);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            item.print(outputStream);

            Assert.assertEquals("dummy: 11.00", outputStream.toString("UTF-8"));
        }

        // imported
        {
            final boolean imported = true;

            final Item item = new Item(name, price, taxable, imported);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            item.print(outputStream);

            Assert.assertEquals("imported dummy: 11.50", outputStream.toString("UTF-8"));
        }
    }
}
