import java.io.FileNotFoundException;
import java.util.HashMap;

public interface EncoderInterface {
    void EncodeText(String FileName) throws FileNotFoundException;

    void StoreMap();
}
