import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Class to generate list of ascii characters
 */
public class ASCII {
    /**
     * Main method.
     *
     * @param args the args
     */
    public static void main(String args[]){
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(
                    "Ascii.txt"));

            for (int i = 0; i < 256; i++) os.writeByte((byte) i);
        os.close();
        }
        catch(Exception e){}

    }
}
