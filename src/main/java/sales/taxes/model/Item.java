/**
 *
 */
package sales.taxes.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Purchasable item
 *
 * @author Ciro Cardone
 *
 */
public class Item {

    /**
     * Item name
     */
    private final String name;

    /**
     * Item price
     */
    private final double price;

    /**
     * true if sales taxes are applicable
     */
    private final boolean taxable;

    /**
     * true if item is imported
     */
    private final boolean imported;

    /**
     * Sales taxes
     */
    private final double taxes;

    /**
     * Create a new item
     *
     * @param name
     * @param price
     * @param taxable
     * @param imported
     */
    public Item(final String name, final double price, final boolean taxable, final boolean imported) {
        super();
        this.name = name;
        this.price = price;
        this.taxable = taxable;
        this.imported = imported;
        this.taxes = computeTaxes();
    }

    /**
     * Prints description of item to given stream
     *
     * @param outputStream
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public void print(final OutputStream outputStream) throws UnsupportedEncodingException, IOException {

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setRoundingMode(RoundingMode.CEILING);

        String description = imported ? "imported " : "";

        description += name + ": " + numberFormat.format(price + taxes);

        outputStream.write(description.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the taxable
     */
    public boolean isTaxable() {
        return taxable;
    }

    /**
     * @return the imported
     */
    public boolean isImported() {
        return imported;
    }

    /**
     * @return the taxes
     */
    public double getTaxes() {
        return taxes;
    }

    /**
     * @return taxes if applicable, or 0
     */
    private double computeTaxes() {

        double t = 0;

        if (taxable) {
            t = price * 0.10;
        }

        if (imported) {
            t += price * 0.05;
        }

        return round(t);
    }

    /**
     *
     * @param toRound
     *
     * @return toRound rounded up to the nearest 0.05
     */
    private double round(final double toRound) {

        return Math.round(toRound * 20.0) / 20.0;

    }

}
