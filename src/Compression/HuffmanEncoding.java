package Compression;

import Compression.TreeEncoder;
import General.Node;
import General.Sort;
import General.TreeGenerator;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;
/**
 * Class for performing Huffman Encoding.
 */
public class HuffmanEncoding extends TreeEncoder implements TreeGenerator {
    public void InitialiseMap(String filename){
        arr=ReadFile(filename);
        for (byte b : arr) {
            char ch = (char) b;
            int count = map.getOrDefault(ch, 0);
            map.put(ch, count + 1);
        }

    }
    @Override
    public void InitialiseTree() {
        PriorityQueue<Node> q=new PriorityQueue<Node>(map.size(),new Sort());
        for(Map.Entry<Character, Integer> entry:map.entrySet()) {
            Node temp=new Node(entry.getKey(),entry.getValue());
            q.add(temp);
        }
        Node root=null;
        if(q.size()==1){
            root=new Node();
            Node single=q.poll();
            root.Left=single;
            root.Freq=single.Freq;
            root.Right=new Node();
        }
        while(q.size()>1) {
            Node left=q.poll();
            Node right=q.poll();
            Node sum=new Node(left.Freq+right.Freq,left,right,Math.max(left.Height,right.Height)+1);
            root=sum;
            q.add(sum);
        }
        Tree=root;
    }

    @Override
    public void SetBitsHash(Node Tree, String bits, HashMap<Character,String> FreqMap) {
        if(Tree==null){
        }
        else if(Tree.Left==null && Tree.Right==null) {
            FreqMap.put(Tree.Char,bits);
        }
        else {
            SetBitsHash(Tree.Left,bits+"0",FreqMap);
            SetBitsHash(Tree.Right,bits+"1",FreqMap);
        }
    }

    @Override
    public void GenerateTreeMap() {
        SetBitsHash(Tree,"",hash);
        for(Map.Entry<Character, String> e : hash.entrySet()) {
            System.out.println(e.getKey()+" | "+e.getValue());
        }
    }



//    OLDER IMPLEMENTATIONS


//    @Override
//    public void StoreMap() {
//        try{
//            FileOutputStream output=new FileOutputStream("Key.txt");
//            ObjectOutputStream serial=new ObjectOutputStream(output);
//            serial.writeObject(map);
//            serial.close();
//            output.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void StoreMap(HashMap<Character, Integer> FreqMap) {
//        try{
//            FileWriter mapfile=new FileWriter("Key.txt");
//            for(Map.Entry<Character, Integer> entry:FreqMap.entrySet()) {
//                if(entry.getKey()=='\n'){
//                    mapfile.write("\\n "+entry.getValue()+"\n");
//                }
//                else
//                mapfile.write(entry.getKey()+" "+entry.getValue()+"\n");
//        }
//        mapfile.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

}
