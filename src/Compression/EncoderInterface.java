package Compression;

import java.io.FileNotFoundException;

/**
 * The interface Encoder interface.
 */
public interface EncoderInterface {
    /**
     * Method to Encode text.
     *
     * @param FileName the input file name
     * @throws FileNotFoundException the file not found exception
     */
    void EncodeText(String FileName) throws FileNotFoundException;

    /**
     * Serialise and map into compressed file.
     */
    void StoreMap();
}
