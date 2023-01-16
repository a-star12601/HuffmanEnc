import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface TreeGenerator {

    void InitialiseMap(String filename);
    void InitialiseTree();
    void GenerateTreeMap();
    void SetBitsHash(Node Tree,String bits,HashMap<Character,String> FreqMap);
}
