package structures.data;

import java.util.Arrays;

/**
 * Represents a CD
 * Instantiate and modifying a CD Record
 */
public class CD {
    private String title, author, description;
    private String section;
    private int xAxis, yAxis;
    private int barcode;
    private boolean onLoan;

    /**
     * Instantiate a new CD that is represented by its barcode
     *
     * @param barcode Barcode of the CD
     */
    public CD(int barcode) {
        this.barcode = barcode;
    }

    /**
     * Instantiate a new CD with all details filled out
     *
     * @param title
     * @param author
     * @param description
     * @param section
     * @param xAxis
     * @param yAxis
     * @param barcode
     * @param onLoan
     */
    public CD(
            String title, String author,
            String description, String section,
            int xAxis, int yAxis,
            int barcode, boolean onLoan
    ) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.section = section;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.barcode = barcode;
        this.onLoan = onLoan;
    }

    //region Getters

    /**
     * Retrieve the title of the CD
     *
     * @return String title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Retrieve the author of the CD
     *
     * @return String author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Retrieve the description of the CD
     *
     * @return String description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieve the section of the CD
     *
     * @return String section
     */
    public String getSection() {
        return this.section;
    }

    /**
     * Retrieve the x axis of the CD
     *
     * @return int xAxis
     */
    public int getXAxis() {
        return this.xAxis;
    }

    /**
     * Retrieve the y axis of the CD
     *
     * @return int yAxis
     */
    public int getYAxis() {
        return this.yAxis;
    }

    /**
     * Retrieve the barcode of the CD
     *
     * @return int barcode
     */
    public int getBarcode() {
        return this.barcode;
    }

    /**
     * Retrieve the on loan status of the CD
     *
     * @return boolean onLoan
     */
    public boolean getOnLoan() {
        return this.onLoan;
    }

    //endregion

    //region Setters

    /**
     * Set the title of the CD
     *
     * @param title New title of the CD
     * @return CD this
     */
    public CD setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the author of the CD
     *
     * @param author New author of the CD
     * @return CD this
     */
    public CD setAuthor(String author) {
        this.author = author;
        return this;
    }

    /**
     * Set the description of the CD
     *
     * @param description New description of the CD
     * @return CD this
     */
    public CD setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set the section of the CD
     *
     * @param section New section of the CD
     * @return CD this
     */
    public CD setSection(String section) {
        this.section = section;
        return this;
    }

    /**
     * Set the xAxis of the CD
     *
     * @param xAxis New x axis of the CD
     * @return CD this
     */
    public CD setXAxis(int xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    /**
     * Set the yAxis of the CD
     *
     * @param yAxis New y axis of the CD
     * @return CD this
     */
    public CD setYAxis(int yAxis) {
        this.yAxis = yAxis;
        return this;
    }

    /**
     * Set the onLoan of the CD
     *
     * @param onLoan New on loan of the CD
     * @return CD this
     */
    public CD setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
        return this;
    }

    //endregion

    /**
     * Convert the CD to a String representation
     *
     * @return String CD
     */
    @Override
    public String toString() {
        return "CD{" +
                "title='" + title + '\'' +
                ",author='" + author + '\'' +
                ",description='" + description + '\'' +
                ",section='" + section + '\'' +
                ",xAxis=" + xAxis +
                ",yAxis=" + yAxis +
                ",barcode=" + barcode +
                ",onLoan=" + onLoan +
                '}';
    }

    /**
     * Return a number between 10000000 and 99999999
     *
     * @return int barcode
     */
    public static int RandomBarcode() {
        return (int) (Math.random() * 89999999 + 10000000);
    }

    /**
     * Util method check if an int is contained within an array
     * Used primarily to check barcodes
     *
     * @param arr An array of barcodes
     * @param barcode
     * @return boolean isContained
     */
    public static boolean ContainsBarcode(final int[] arr, final int barcode) {
        return Arrays.stream(arr).anyMatch(num -> num == barcode);
    }
}
