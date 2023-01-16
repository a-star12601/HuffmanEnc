import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ASCII {
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
