package root.compression;

import root.general.Node;
import root.general.Sort;
import root.general.TreeGenerator;

import java.io.IOException;
import java.util.*;
/**
 * Class for performing Huffman Encoding.
 */
public class HuffmanEncoding implements TreeGenerator {

    public HashMap<Character,Integer> initialiseMap(byte[] arr)throws IOException, ClassNotFoundException {
        HashMap<Character,Integer> map=new HashMap<>();
        for (byte b : arr) {
            char ch = (char) b;
            int count = map.getOrDefault(ch, 0);
            map.put(ch, count + 1);
        }
        return map;
    }
    @Override
    public Node initialiseTree(HashMap<Character,Integer> map) {
        Node tree;
        PriorityQueue<Node> q=new PriorityQueue<>(map.size(),new Sort());
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
        tree =root;
        return tree;
    }
    public void setBitsHash(Node tree, String bits, HashMap<Character,String> freqMap) {
        if(tree ==null){
            //do nothing
        }
        else if(tree.Left==null && tree.Right==null) {
            freqMap.put(tree.Char,bits);
        }
        else {
            setBitsHash(tree.Left,bits+"0",freqMap);
            setBitsHash(tree.Right,bits+"1",freqMap);
        }
    }

    @Override
    public HashMap< Character,String> generateTreeMap(Node tree) {
        HashMap< Character,String > hash=new HashMap<>();
        setBitsHash(tree,"",hash);
        return hash;
        /*
        for(Map.Entry<Character, String> e : hash.entrySet()) {
            System.out.println(e.getKey()+" | "+e.getValue());
        }
         */
    }


}
