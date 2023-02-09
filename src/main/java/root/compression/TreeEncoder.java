package root.compression;

import root.general.FileOperations;
import root.general.Node;

import java.io.*;
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
        List<Byte> bytes=encodingLogic(arr,hash);
        byte[] exportBytes=byteFromByteList(bytes);
        writeToFile("Compressed.txt",true,exportBytes);

    }
    public void storeMap(String compressedPath) {
        try{
            byte[] b=getMapBytes(map);
            mapsize=b.length;
            writeToFile(compressedPath,false,(mapsize+"\n").getBytes());
            writeToFile(compressedPath,true,b);
            }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Byte> encodingLogic(byte[] arr,HashMap<Character,String> hash){
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
        return bytes;
    }
    public byte[] getMapBytes(HashMap<Character,Integer> map) throws IOException {
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        ObjectOutputStream serial=new ObjectOutputStream(bStream);
        serial.writeObject(map);
        serial.close();
        byte[] b= bStream.toByteArray();
        bStream.close();
        return b;
    }
}
