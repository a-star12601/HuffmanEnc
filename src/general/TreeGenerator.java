package general;

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
    void initialiseMap(String filename);

    /**
     * Generate the Tree from the Hashmap.
     */
    void initialiseTree();

    /**
     * Generate character-mapping using tree.
     */
    void generateTreeMap();

    /**
     * Generate the hash string for each character.
     *
     * @param tree    the tree
     * @param bits    current string
     * @param map     the map storing the mappings
     */
    void setBitsHash(Node tree, String bits, HashMap<Character,String> map);
}
