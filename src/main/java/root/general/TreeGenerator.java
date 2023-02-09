package root.general;

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
    Node initialiseTree(HashMap<Character,Integer> map);

    /**
     * Generate character-mapping using tree.
     */
    void generateTreeMap(Node tree);
}
