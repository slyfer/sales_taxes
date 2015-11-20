/**
 *
 */
package sales.taxes.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;

/**
 * Item in a basket
 *
 * @author Ciro Cardone
 *
 */
public class BasketItem extends Item {

    /**
     * Quantity of product in the basket
     */
    private final int quantity;

    public BasketItem(final int quantity, final String name, final double price, final boolean taxable, final boolean imported) {
        super(name, price, taxable, imported);
        this.quantity = quantity;
    }

    @Override
    public double getPrice() {
        return super.getPrice() * quantity;
    }

    @Override
    public double getTaxes() {
        return super.getTaxes() * quantity;
    }

    /**
     * Prints item and close stream
     *
     * @see Item#print(OutputStream)
     */
    @Override
    public void print(final OutputStream outputStream) throws UnsupportedEncodingException, IOException {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        String description = quantity + " ";

        description += isImported() ? "imported " : "";

        description += getName() + ": " + numberFormat.format(getPrice() + getTaxes());

        outputStream.write(description.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

}
