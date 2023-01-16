import java.io.*;
import java.util.*;

/**
 * Class for performing Huffman Encoding.
 */
public class HuffmanEncoding extends FileOperations implements TreeGenerator,EncoderInterface {

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
    @Override
    public void InitialiseMap(String filename){
        byte[] arr=ReadFile(filename);
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
    public void SetBitsHash(Node Tree,String bits,HashMap<Character,String> FreqMap) {
        if(Tree.Left==null && Tree.Right==null) {
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
    }

    @Override
    public void EncodeText(String FileName) throws FileNotFoundException {
        String ByteArr="";
        String CurrentByte="";
        try {
            FileOutputStream fout = new FileOutputStream(mapsize+".txt",true);
            byte[] arr = ReadFile(FileName);
            for (byte c : arr) {
                char ch = (char) c;
                ByteArr+=hash.get(ch);
                if(ByteArr.length()>8){
                    CurrentByte=ByteArr.substring(0,8);
                    ByteArr=ByteArr.substring(8);
                    byte b = (byte) Integer.parseInt(CurrentByte, 2);
                    fout.write(b);
                }
            }
            while(ByteArr.length()>=8) {
                CurrentByte = ByteArr.substring(0, 8);
                ByteArr = ByteArr.substring(8);
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                fout.write(b);
            }
            if(ByteArr.length()>0){
                CurrentByte=String.format("%1$-" + 8 + "s", ByteArr).replace(' ', '0');
                ByteArr="";
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                fout.write(b);
            }
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void StoreMap() {
        try{

            ByteArrayOutputStream bStream=new ByteArrayOutputStream();
            ObjectOutputStream serial=new ObjectOutputStream(bStream);
            serial.writeObject(map);
            serial.close();
            byte[] b= bStream.toByteArray();
            mapsize=b.length;
            //System.out.println(mapsize);
            FileOutputStream output=new FileOutputStream(mapsize+".txt");
            output.write(b);
            output.close();
            bStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
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
