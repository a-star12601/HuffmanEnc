package Compression;

import java.io.FileNotFoundException;

/**
 * The interface for a general Encoder.
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
     * Serialise and store map into compressed file.
     */
    void StoreMap();
}
