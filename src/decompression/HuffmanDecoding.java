package decompression;

import general.Node;
import general.Sort;
import general.TreeGenerator;

import java.io.*;
import java.util.*;

/**
 * Class for performing Huffman decoding.
 */
public class HuffmanDecoding extends TreeDecoder implements TreeGenerator {

    @Override
    public void initialiseMap(String filename) {
        try{
            arr= readFile(filename);
//            System.out.println(arr.length);
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
            byte[] b1= Arrays.copyOfRange(arr,i+1,(int)mapsize);
            ByteArrayInputStream bStream=new ByteArrayInputStream(b1);
            ObjectInputStream serial=new ObjectInputStream(bStream);
            map=(HashMap<Character, Integer>) serial.readObject();
            serial.close();
            bStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialiseTree() {
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
    }

    @Override
    public void generateTreeMap() {
        setBitsHash(tree, "", hash);
/*
        for (Map.Entry<Character, String> e : hash.entrySet()) {
            System.out.println(e.getKey() + " | " + e.getValue());
        }
*/
    }

    @Override
    public void setBitsHash(Node tree, String bits, HashMap<Character, String> freqMap) {
            if(tree.Left==null && tree.Right==null) {
                freqMap.put(tree.Char,bits);
            }
            else {
                setBitsHash(tree.Left,bits+"0",freqMap);
                setBitsHash(tree.Right,bits+"1",freqMap);
            }
    }



}

/*
   OLDER IMPLEMENTATION
    public void InitialiseMap(String filename) {
        try{
            FileInputStream input=new FileInputStream(filename);
            ObjectInputStream serial=new ObjectInputStream(input);
            map=(HashMap<Character, Integer>) serial.readObject();
            serial.close();
            input.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public HashMap<Character, Integer> InitialiseMap(String filename){
        HashMap<Character, Integer> map = new HashMap<>();
        try {
            File mapfile = new File(filename);
            Scanner s = new Scanner(mapfile);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.substring(0, 2).equals("\\n")) {
                    map.put('\n', Integer.parseInt(line.substring(3)));
                } else
                    map.put(line.charAt(0), Integer.parseInt(line.substring(2)));
            }
            s.close();
        }
        catch (Exception e){}
            return map;
    }
    @Override
    public void DecodeText(General.Node Tree, String filename,long count) {
        try {
            System.out.println("Decompressing");
            File compressed=new File(filename);
            FileInputStream input = new FileInputStream(compressed);
            File decomp=new File("Decompressed.txt");
            FileOutputStream output= new FileOutputStream(decomp);
            byte[] arr = new byte[(int)compressed.length()+1];
            input.read(arr);
            input.close();
            String CurrentByte;
            int curbyte=0;
            General.Node root=Tree;
            byte b=arr[curbyte];
            int chars=0;
            CurrentByte=String.format("%8s", Integer.toBinaryString((b + 256) % 256))
                    .replace(' ', '0');
            while(curbyte<(int)compressed.length()){
                //System.out.println("Out");
                while(root.Left!=null && root.Right!=null){
                    //System.out.println("In");
                    if(CurrentByte.length()==0){
                        b=arr[++curbyte];
                        CurrentByte=String.format("%8s", Integer.toBinaryString((b + 256) % 256))
                                .replace(' ', '0');
                    }
                    else if(CurrentByte.charAt(0)=='0') {
                        CurrentByte = CurrentByte.substring(1);
                        root = root.Left;
                    }
                    else if(CurrentByte.charAt(0)=='1') {
                        CurrentByte = CurrentByte.substring(1);
                        root = root.Right;
                    }
                }
                output.write((byte)root.Char);
                chars++;
                if(chars==count-1){break;}
                root=Tree;
            }
            //System.out.println(chars+" "+count);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/





