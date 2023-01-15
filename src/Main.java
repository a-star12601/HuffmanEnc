import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws FileNotFoundException {
        HuffmanEncoding demo=new HuffmanEncoding();
        String Filename="pg100.txt";
        HashMap<Character,Integer> FreqMap=demo.InitialiseMap(Filename);
        Node Tree=demo.InitialiseTree(FreqMap);
        for(Map.Entry<Character, Integer> entry:FreqMap.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        HashMap<Character,String> TMap=demo.GenerateTreeMap(Tree);
//        for(Map.Entry<Character, String> entry:TMap.entrySet()) {
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }
        System.out.print("\n");
        demo.EncodeText(TMap,Filename);
        demo.StoreMap(FreqMap);
        HuffmanDecoding decode=new HuffmanDecoding();
        HashMap<Character,Integer> DecodeMap=decode.InitialiseMap("Key.txt");
        for(Map.Entry<Character, Integer> entry:DecodeMap.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        long count=decode.getCount(DecodeMap);
        Node DecodeTree=decode.InitialiseTree(FreqMap);
        System.out.print("\n");
        decode.DecodeText(DecodeTree,"Compressed.txt ",count);

    }


}