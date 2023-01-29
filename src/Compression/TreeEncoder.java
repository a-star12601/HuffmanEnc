import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TreeEncoder extends FileOperations implements EncoderInterface{
    /**
     * Hashmap storing character frequencies.
     */
    HashMap<Character,Integer> map=new HashMap<>();
    /**
     * Data Structure for storing the Huffman Tree.
     */
    Node Tree;
    /**
     * Hashmap storing character and corresponding Huffman code.
     */
    HashMap<Character, String> hash=new HashMap<>();
    /**
     * variable storing size of Serialised Hashmap.
     */
    long mapsize;

    byte[] arr;

    public void EncodeText(String fileName) throws FileNotFoundException {
        String ByteArr="";
        String CurrentByte="";
        try {
            FileOutputStream fout = new FileOutputStream("Compressed.txt",true);
            for (byte c : arr) {
                char ch = (char) c;
                ByteArr+=hash.get(ch);
                if(ByteArr.length()>8){
                    CurrentByte=ByteArr.substring(0,8);
                    ByteArr=ByteArr.substring(8);
                    byte b = (byte) Integer.parseInt(CurrentByte, 2);
                    fout.write(b);
                }
            }
            while(ByteArr.length()>=8) {
                CurrentByte = ByteArr.substring(0, 8);
                ByteArr = ByteArr.substring(8);
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                fout.write(b);
            }
            if(ByteArr.length()>0){
                CurrentByte=String.format("%1$-" + 8 + "s", ByteArr).replace(' ', '0');
                ByteArr="";
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                fout.write(b);
            }
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void StoreMap() {
        try{

            ByteArrayOutputStream bStream=new ByteArrayOutputStream();
            ObjectOutputStream serial=new ObjectOutputStream(bStream);
            serial.writeObject(map);
            serial.close();
            byte[] b= bStream.toByteArray();
            bStream.close();
            mapsize=b.length;
            //System.out.println(mapsize);
            FileOutputStream output=new FileOutputStream("Compressed.txt");
            output.write((mapsize+"\n").getBytes());
            output.write(b);
            output.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
