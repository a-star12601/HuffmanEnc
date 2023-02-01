package Decompression;

import Decompression.DecoderInterface;
import General.FileOperations;
import General.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeDecoder extends FileOperations implements DecoderInterface {
    /**
     * Character frequency hashmap.
     */
    HashMap<Character,Integer> map;
    /**
     * Data Structure for storing Huffman Tree.
     */
    Node Tree;
    /**
     * variable storing no. of characters to be read.
     */
    long count;
    /**
     * variable storing size of serialised map.
     */
    long mapsize;
    HashMap<Character, String> hash=new HashMap<>();

    byte[] arr;

    @Override
    public void DecodeText(String filename) {
        try {
            //System.out.println("Decompressing");
            File decomp=new File("Decompressed.txt");
            List<Byte> bytes=new ArrayList<>();

            int curbyte=(int)mapsize;
            Node root=Tree;
            byte b=arr[curbyte];
            int chars=0;
            int bitcounter=0;
            boolean[] bits = new boolean[8];
            for (int i = 0; i < 8; i++)
                bits[7 - i] = ((b & (1 << i)) != 0);
            while(curbyte<(int)arr.length){
                //System.out.println("Out");
                while(root.Left!=null && root.Right!=null){
                    //System.out.println("In");
                    if(bitcounter==8){
                        b=arr[++curbyte];
                        for (int i = 0; i < 8; i++)
                            bits[7 - i] = ((b & (1 << i)) != 0);
                        bitcounter=0;
                    }
                    else if(false == bits[bitcounter]) {
                        bitcounter++;
                        root = root.Left;
                    }
                    else if(true == bits[bitcounter]) {
                        bitcounter++;
                        root = root.Right;
                    }
                }
                bytes.add((byte)root.Char);
                chars++;
                if(chars==count){break;}
                root=Tree;
            }
            byte[] exportBytes=new byte[bytes.size()];
            int i=0;
            for(Byte c:bytes){
                exportBytes[i++]=c.byteValue();
            }
            FileOutputStream output= new FileOutputStream(decomp);
            output.write(exportBytes);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCount() {
        count=0;
        for(Map.Entry<Character, Integer> entry:map.entrySet()){
            count+=entry.getValue();
        }
        //System.out.println(count);
    }


}

