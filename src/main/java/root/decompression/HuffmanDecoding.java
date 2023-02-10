package root.decompression;

import root.general.Node;
import root.general.Sort;
import root.general.TreeGenerator;

import java.io.*;
import java.util.*;

/**
 * Class for performing Huffman decoding.
 */
public class HuffmanDecoding implements TreeGenerator {

    int mapsize=0;
    public int getMapSize(){
        return mapsize;
    }
    @Override
    public HashMap<Character,Integer> initialiseMap(byte[] arr) throws IOException, ClassNotFoundException {
        if(arr==null || arr.length==0){
            throw new RuntimeException("Input file is Empty");
        }
        HashMap<Character,Integer> map=new HashMap<>();
        int i=0;
        for(byte x:arr){
            if((char) x=='\n'){
                break;
            }
            else
                mapsize=mapsize*10+Integer.parseInt((char)x+"");
            i++;
        }
        mapsize=mapsize+i+1;
        //System.out.println(mapsize);
        byte[] b1= Arrays.copyOfRange(arr,i+1,(int)mapsize);
        ByteArrayInputStream bStream=new ByteArrayInputStream(b1);
        ObjectInputStream serial=new ObjectInputStream(bStream);
        map=(HashMap<Character, Integer>) serial.readObject();
        serial.close();
        bStream.close();
        return map;
    }
    @Override
    public Node initialiseTree(HashMap<Character,Integer> map) {
        Node tree=new Node();
        if(map==null||map.size()==0){
            throw new RuntimeException("Map is empty!!");
        }
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
        tree=root;
        return tree;
    }

    @Override
    public HashMap<Character,String> generateTreeMap(Node tree) {
        return null;
 /*
        setBitsHash(tree, "", hash);
       for (Map.Entry<Character, String> e : hash.entrySet()) {
            System.out.println(e.getKey() + " | " + e.getValue());
        }
*/
    }
}
