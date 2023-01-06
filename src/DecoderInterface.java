import java.util.HashMap;

public interface DecoderInterface {
    void DecodeText(Node Tree,String filename,long count);

    long getCount(HashMap<Character,Integer> Mapt);

}
