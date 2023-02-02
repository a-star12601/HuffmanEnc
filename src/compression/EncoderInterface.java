package compression;

import java.io.FileNotFoundException;

/**
 * The interface for a general Encoder.
 */
public interface EncoderInterface {
    /**
     * Method to Encode text.
     *
     * @param fileName the input file name
     * @throws FileNotFoundException the file not found exception
     */
    void encodeText(String fileName) throws FileNotFoundException;

    /**
     * Serialise and store map into compressed file.
     */
    void storeMap();
}
