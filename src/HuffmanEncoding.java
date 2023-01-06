import java.io.*;
import java.util.*;

public class HuffmanEncoding implements TreeGenerator,EncoderInterface{
    @Override
    public HashMap<Character, Integer> InitialiseMap(String filename) throws FileNotFoundException {
        File f0=new File(filename);
        Scanner s=new Scanner(f0);
        HashMap<Character,Integer> map=new HashMap<>();
        while(s.hasNextLine()) {
            String line = s.nextLine();
            for (char ch : line.toCharArray()) {
                int count = map.containsKey(ch) ? map.get(ch) : 0;
                map.put(ch, count + 1);
            }
        }
        s.close();
        return map;
    }

    @Override
    public Node InitialiseTree(HashMap<Character,Integer> map) {
        PriorityQueue<Node> q=new PriorityQueue<Node>(map.size(),new Sort());
        for(Map.Entry<Character, Integer> entry:map.entrySet()) {
            Node temp=new Node();
            temp.Char=entry.getKey();
            temp.Freq=entry.getValue();
            temp.Left=null;
            temp.Right=null;
            temp.Height=0;
            q.add(temp);
        }
        Node root=null;
        while(q.size()>1) {
            //System.out.println(q);
            Node left=q.poll();
            Node right=q.poll();

            Node sum=new Node();
            sum.Freq=left.Freq+right.Freq;
            sum.Char='\0';
            sum.Left=left;
            sum.Right=right;
            sum.Height=Math.max(left.Height,right.Height)+1;
            root=sum;
            q.add(sum);
        }
        return root;
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
    public HashMap<Character, String> GenerateTreeMap(Node Tree) {
        HashMap<Character,String> hash=new HashMap<>();
        SetBitsHash(Tree,"",hash);
        return hash;
    }

    @Override
    public void EncodeText(HashMap<Character, String> TreeMap, String FileName) throws FileNotFoundException {
        File input=new File(FileName);
        Scanner s=new Scanner(input);
        String ByteArr="";
        String CurrentByte="";
        try {
            FileOutputStream fout = new FileOutputStream("Compressed.txt");
            while (s.hasNextLine()) {
                String line = s.nextLine();
                for (char ch : line.toCharArray()) {
                    ByteArr+=TreeMap.get(ch);
                    if(ByteArr.length()>8){
                        CurrentByte=ByteArr.substring(0,8);
                        ByteArr=ByteArr.substring(8);
                        System.out.println(CurrentByte);
                        byte b = (byte) Integer.parseInt(CurrentByte, 2);
                        fout.write(b);
                    }
                }
            }
            while(ByteArr.length()>=8) {
                CurrentByte = ByteArr.substring(0, 8);
                ByteArr = ByteArr.substring(8);
                System.out.println(CurrentByte);
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                fout.write(b);
            }
            if(ByteArr.length()>0){
                CurrentByte=String.format("%1$-" + 8 + "s", ByteArr).replace(' ', '0');
                ByteArr="";
                System.out.println(CurrentByte);
                byte b = (byte) Integer.parseInt(CurrentByte, 2);
                fout.write(b);
            }
            fout.close();
        }
        catch (Exception e){System.out.println(e);}
        s.close();
    }

    @Override
    public void StoreMap(HashMap<Character, Integer> FreqMap) {
        try{
            FileWriter mapfile=new FileWriter("Key.txt");
            for(Map.Entry<Character, Integer> entry:FreqMap.entrySet()) {
                mapfile.write(entry.getKey()+" "+entry.getValue()+"\n");
        }
        mapfile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
