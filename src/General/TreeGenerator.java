import java.io.FileNotFoundException;
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
    void InitialiseMap(String filename);

    /**
     * Generate the Tree from the Hashmap.
     */
    void InitialiseTree();

    /**
     * Generate character-mapping using tree.
     */
    void GenerateTreeMap();

    /**
     * Generate the hash string for each character.
     *
     * @param Tree    the tree
     * @param bits    current string
     * @param map     the map storing the mappings
     */
    void SetBitsHash(Node Tree,String bits,HashMap<Character,String> map);
}
