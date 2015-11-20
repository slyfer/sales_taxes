/**
 *
 */
package sales.taxes.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Item in a basket
 *
 * @author Ciro Cardone
 *
 */
public class BasketItem extends Item {

    private final int quantity;

    public BasketItem(final int quantity, final String name, final double price, final boolean taxable, final boolean imported) {
        super(name, price, taxable, imported);
        this.quantity = quantity;
    }

    /**
     * Adds quantity to description
     */
    @Override
    public void print(final OutputStream outputStream) throws UnsupportedEncodingException, IOException {

        final String quantityDescription = quantity + " ";
        outputStream.write(quantityDescription.getBytes("UTF-8"));
        super.print(outputStream);
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

}
