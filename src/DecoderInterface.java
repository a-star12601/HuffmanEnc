import java.util.HashMap;

/**
 * The Decoder interface.
 */
public interface DecoderInterface {
    /**
     * Decode text from compressed file.
     *
     * @param filename the filename
     */
    void DecodeText(String filename);

    /**
     * gets no of characters in the file.
     */
    void getCount();

}
