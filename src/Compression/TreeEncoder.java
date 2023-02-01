package Compression;

import Compression.EncoderInterface;
import General.FileOperations;
import General.Node;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for general Tree-Based Encoders.
 */
public class TreeEncoder extends FileOperations implements EncoderInterface {
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

    /**
     * Byte Array storing file contents.
     */
    byte[] arr;

    public void EncodeText(String fileName) throws FileNotFoundException {
        String ByteArr="";
        String CurrentByte="";
        try {
            List<Byte> bytes=new ArrayList<>();
            for (byte c : arr) {
                char ch = (char) c;
                ByteArr+=hash.get(ch);
                if(ByteArr.length()>8){
                    CurrentByte=ByteArr.substring(0,8);
                    ByteArr=ByteArr.substring(8);
                    byte b = (byte) Integer.parseInt(CurrentByte, 2);
                    bytes.add(b);
                }
            }
            while(ByteArr.length()>=8) {
                CurrentByte = ByteArr.substring(0, 8);
                ByteArr = ByteArr.substring(8);
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                bytes.add(b);
            }
            if(ByteArr.length()>0){
                CurrentByte=String.format("%1$-" + 8 + "s", ByteArr).replace(' ', '0');
                ByteArr="";
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                bytes.add(b);
            }

            byte[] exportBytes=new byte[bytes.size()];
            int i=0;
            for(Byte b:bytes){
                exportBytes[i++]=b.byteValue();
            }
            FileOutputStream fout = new FileOutputStream("Compressed.txt",true);
            fout.write(exportBytes);
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
