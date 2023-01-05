import java.io.FileNotFoundException;
import java.util.HashMap;

public interface EncoderInterface {
    void EncodeText(HashMap<Character,String> TreeMap,String FileName) throws FileNotFoundException;
}
