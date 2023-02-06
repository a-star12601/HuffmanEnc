package root.compression;

import root.general.FileOperations;
import root.general.Node;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for root.general Tree-Based Encoders.
 */
public class TreeEncoder extends FileOperations implements EncoderInterface {
    /**
     * Hashmap storing character frequencies.
     */
    public HashMap<Character,Integer> map=new HashMap<>();
    /**
     * Data Structure for storing the Huffman Tree.
     */
    public Node tree;
    /**
     * Hashmap storing character and corresponding Huffman code.
     */
    public HashMap<Character, String> hash=new HashMap<>();
    /**
     * variable storing size of Serialised Hashmap.
     */
    long mapsize;

    /**
     * Byte Array storing file contents.
     */
    byte[] arr;

    public void encodeText(String fileName) throws FileNotFoundException {
        String byteArr="";
        String currentByte="";
        List<Byte> bytes=new ArrayList<>();
        for (byte c : arr) {
            char ch = (char) c;
            byteArr+=hash.get(ch);
            while(byteArr.length()>=8){
                currentByte=byteArr.substring(0,8);
                byteArr=byteArr.substring(8);
                byte b = (byte) Integer.parseInt(currentByte, 2);
                bytes.add(b);
            }
        }
        if(byteArr.length()>0){
            currentByte=String.format("%1$-" + 8 + "s", byteArr).replace(' ', '0');
            byte b = (byte) Integer.parseInt(currentByte, 2);
            bytes.add(b);
        }
        byte[] exportBytes=byteFromByteList(bytes);
        try (FileOutputStream fout = new FileOutputStream("Compressed.txt", true)) {
            fout.write(exportBytes);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void storeMap() {
        try{
            ByteArrayOutputStream bStream=new ByteArrayOutputStream();
            ObjectOutputStream serial=new ObjectOutputStream(bStream);
            serial.writeObject(map);
            serial.close();
            byte[] b= bStream.toByteArray();
            bStream.close();
            mapsize=b.length;
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
