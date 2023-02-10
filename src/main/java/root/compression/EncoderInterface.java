package root.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The interface for a root.general Encoder.
 */
public interface EncoderInterface {
    /**
     * Method to Encode text.
     *
     * @param fileName the input file name
     * @throws FileNotFoundException the file not found exception
     */
    List<Byte> encodingLogic(byte[] arr, HashMap<Character,String> hash);

    /**
     * Serialise and store map into compressed file.
     */
    byte[] storeMap(HashMap<Character,Integer> map) throws IOException;
}
