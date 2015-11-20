/**
 *
 */
package sales.taxes.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping Basket
 *
 * @author Ciro Cardone
 *
 */
public class Basket {

    private static final String NEW_LINE = "\n";

    /**
     * Total of taxes
     */
    private double salesTaxes;

    /**
     * Total price
     */
    private double total;

    /**
     * Items in basket
     */
    private int numberOfItems;

    /**
     * Items
     */
    private final List<BasketItem> items;

    /**
     * Creates empty basket
     */
    public Basket() {
        super();

        items = new ArrayList<BasketItem>();

        clear();
    }

    /**
     * Adds basketItem to basket
     *
     * @param basketItem
     */
    public void add(final BasketItem basketItem) {

        items.add(basketItem);

        final int quantity = basketItem.getQuantity();
        final double itemTaxes = basketItem.getTaxes();
        final double itemTotalPrice = basketItem.getPrice() + itemTaxes;

        salesTaxes += itemTaxes;
        total += itemTotalPrice;
        numberOfItems += quantity;

    }

    /**
     * Prints description of basket to given stream closes it
     *
     * @param outputStream
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public void print(final OutputStream outputStream) throws UnsupportedEncodingException, IOException {

        final String charsetName = "UTF-8";
        final byte[] newLineBytes = NEW_LINE.getBytes(charsetName);

        // prints each item
        for (final BasketItem basketItem : items) {

            final ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
            basketItem.print(tempStream);

            outputStream.write(tempStream.toByteArray());
            outputStream.write(newLineBytes);
        }

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        // adding taxes and total
        final String salesTaxesString = "Sales Taxes: " + numberFormat.format(getSalesTaxes());
        final String totalString = "Total: " + numberFormat.format(getTotal());

        outputStream.write(salesTaxesString.getBytes(charsetName));
        outputStream.write(newLineBytes);
        outputStream.write(totalString.getBytes(charsetName));

        outputStream.flush();
        outputStream.close();
    }

    /**
     * Clear all items in basket
     */
    public void clear() {
        salesTaxes = 0;
        total = 0;
        numberOfItems = 0;
        items.clear();
    }

    /**
     * @return the salesTaxes
     */
    public double getSalesTaxes() {
        return salesTaxes;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @return the numberOfItems
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

}
