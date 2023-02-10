package root.decompression;

import root.general.Node;

import java.util.HashMap;
import java.util.List;

/**
 * The Decoder interface.
 */
public interface DecoderInterface {
    /**
     * Decode text from compressed file.
     *
     * @param filename the filename
     */
    List<Byte> decodingLogic(byte[] arr, Node tree, long mapsize, long count);

    /**
     * gets no of characters in the file.
     */
    int getCount(HashMap<Character,Integer> map);

}
