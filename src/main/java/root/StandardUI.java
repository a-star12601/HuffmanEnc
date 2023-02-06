package root;

/**
 * The Interface which provides basic UI operations.
 */
public interface StandardUI {

    /**
     * Perform Encode Operation.
     */
    boolean encode(String filename);

    /**
     * Perform Decode Operation.
     */
    boolean decode(String filename,String compressed);


}