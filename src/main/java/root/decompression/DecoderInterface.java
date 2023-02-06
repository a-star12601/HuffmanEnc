package root.decompression;

/**
 * The Decoder interface.
 */
public interface DecoderInterface {
    /**
     * Decode text from compressed file.
     *
     * @param filename the filename
     */
    void decodeText(String filename);

    /**
     * gets no of characters in the file.
     */
    void getCount();

}
