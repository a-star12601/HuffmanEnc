package root.decompression;

import root.general.FileOperations;
import root.general.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for root.general Tree-Based Decoders.
 */
public class TreeDecoder extends FileOperations implements DecoderInterface {
    /**
     * Character frequency hashmap.
     */
    HashMap<Character,Integer> map;
    /**
     * Data Structure for storing Huffman Tree.
     */
    Node tree;
    /**
     * variable storing no. of characters to be read.
     */
    long count;
    /**
     * variable storing size of serialised map.
     */
    long mapsize;
    /**
     * The Hashmap storing code mappings.
     */
    HashMap<Character, String> hash=new HashMap<>();

    /**
     * Byte Array storing file contents.
     */
    byte[] arr;

    @Override
    public void decodeText(String filename) {
        List<Byte> bytes=decodingLogic(arr,tree,mapsize,count);
        byte[] exportBytes=byteFromByteList(bytes);
        writeToFile("Decompressed.txt",false,exportBytes);
    }

    @Override
    public void getCount() {
        count=0;
        for(Map.Entry<Character, Integer> entry:map.entrySet()){
            count+=entry.getValue();
        }
        //System.out.println(count);
    }

    public List<Byte> decodingLogic(byte[] arr,Node tree,long mapsize,long count){
        List<Byte> bytes=new ArrayList<>();
        int curbyte=(int)mapsize;
        Node root= tree;
        byte b=arr[curbyte];
        int chars=0;
        int bitcounter=0;
        boolean[] bits = new boolean[8];
        for (int i = 0; i < 8; i++)
            bits[7 - i] = ((b & (1 << i)) != 0);
        while(curbyte<arr.length){
            while(root.Left!=null && root.Right!=null){
                if(bitcounter==8){
                    b=arr[++curbyte];
                    for (int i = 0; i < 8; i++)
                        bits[7 - i] = ((b & (1 << i)) != 0);
                    bitcounter=0;
                }
                else if(!bits[bitcounter]) {
                    bitcounter++;
                    root = root.Left;
                }
                else{
                    bitcounter++;
                    root = root.Right;
                }
            }
            bytes.add((byte)root.Char);
            chars++;
            if(chars==count){break;}
            root= tree;
        }
        return bytes;
    }


}

