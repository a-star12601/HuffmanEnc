import java.io.FileNotFoundException;
import java.util.HashMap;

public interface TreeGenerator {

    HashMap<Character,Integer> InitialiseMap(String filename) throws FileNotFoundException;
    Node InitialiseTree(HashMap<Character,Integer> FreqMap);
    HashMap<Character,String> GenerateTreeMap(Node Tree);
    void SetBitsHash(Node Tree,String bits,HashMap<Character,String> FreqMap);
}
