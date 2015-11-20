/**
 *
 */
package sales.taxes.model;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test class for {@link Item}, only check taxes rounding
 *
 * @author Ciro Cardone
 *
 */
@RunWith(Parameterized.class)
public class ItemTaxesRoundingTest {

    @Parameters(name = "{index}: price={0}, taxable={1}, imported={2}, expectedTaxes={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                // simple, no rounding needed
                { 10.0, true, true, 1.5 },

                // simple, no rounding needed
                { 10.0, false, true, 0.5 },

                // rounding 2.459 -> 2.5
                { 24.59, true, false, 2.5 },

                // rounding 2.440 -> 2.45
                { 24.40, true, false, 2.45 },

                // rounding 3.66 -> 3.70
                { 24.40, true, true, 3.70 },

                // rounding 4.9995 -> 5.0
                { 33.33, true, true, 5.0 },

                // rounding 0.5625 -> 0.6
                { 11.25, false, true, 0.6 }

        });
    }

    private final double price;

    private final boolean taxable;

    private final boolean imported;

    private final double expectedTaxes;

    public ItemTaxesRoundingTest(final double price, final boolean taxable, final boolean imported, final double expectedTaxes) {
        super();
        this.price = price;
        this.taxable = taxable;
        this.imported = imported;
        this.expectedTaxes = expectedTaxes;
    }

    /**
     * Verifies taxes rounding
     */
    @Test
    public void test() {

        final String name = "dummy";

        final Item item = new Item(name, price, taxable, imported);

        Assert.assertEquals(expectedTaxes, item.getTaxes(), 0.001);
    }

}
