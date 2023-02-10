package root.decompression;

import root.general.FileOperations;
import root.general.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for root.general Tree-Based Decoders.
 */
public class TreeDecoder implements DecoderInterface {
    /**
     * variable storing size of serialised map.
     */
    long mapsize;

    @Override
    public int getCount(HashMap<Character,Integer> map) {
        int count=0;
        for(Map.Entry<Character, Integer> entry:map.entrySet()){
            count+=entry.getValue();
        }
        return count;
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

