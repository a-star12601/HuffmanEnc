package root.general;

import java.io.IOException;
import java.util.HashMap;

/**
 * Interface to generate Tree Structure.
 */
public interface TreeGenerator {

    /**
     * Initialise the character frequency hashmap.
     * In Encoding: reads the frequency and stores it in map.
     * In Decoding: reads the serialised hashmap.
     *
     * @param filename Name of input file
     */
    HashMap<Character,Integer> initialiseMap(byte[] arr) throws IOException, ClassNotFoundException ;

    /**
     * Generate the Tree from the Hashmap.
     */
    Node initialiseTree(HashMap<Character,Integer> map);

    /**
     * Generate character-mapping using tree.
     */
    HashMap<Character,String> generateTreeMap(Node tree);
}
