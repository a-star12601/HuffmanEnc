import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanDecoding implements TreeGenerator,DecoderInterface{

    @Override
    public HashMap<Character, Integer> InitialiseMap(String filename) throws FileNotFoundException {
        File mapfile=new File(filename);
        Scanner s=new Scanner(mapfile);
        HashMap<Character,Integer> map=new HashMap<>();
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.substring(0,2).equals("\\n")){
                map.put('\n',Integer.parseInt(line.substring(3)));
            }
            else
            map.put(line.charAt(0),Integer.parseInt(line.substring(2)));
        }
        s.close();
        return map;
    }

    @Override
    public Node InitialiseTree(HashMap<Character, Integer> FreqMap) {
        PriorityQueue<Node> q=new PriorityQueue<Node>(FreqMap.size(),new Sort());
        for(Map.Entry<Character, Integer> entry:FreqMap.entrySet()) {
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
    public HashMap<Character, String> GenerateTreeMap(Node Tree) {
        return null;
    }

    @Override
    public void SetBitsHash(Node Tree, String bits, HashMap<Character, String> FreqMap) {

    }

    @Override
    public void DecodeText(Node Tree, String filename,long count) {
        try {
            File compressed=new File(filename);
            FileInputStream input = new FileInputStream(compressed);
            File output=new File("Decompressed.txt");
            FileWriter out=new FileWriter(output);
            byte[] arr = new byte[(int)compressed.length()+1];
            input.read(arr);
            input.close();
//            for(byte b:arr){
//                System.out.println(String.format("%8s", Integer.toBinaryString((b + 256) % 256))
//                        .replace(' ', '0'));
//            }
            String CurrentByte;
            int curbyte=0;
            Node root=Tree;
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
                if(root.Char=='\n')
                    out.write("\n");
                else
                    out.write(root.Char);
                chars++;
                if(chars==count-1){break;}
                root=Tree;
            }
            System.out.println(chars+" "+count);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getCount(HashMap<Character, Integer> Map) {
        long count=0;
        for(Map.Entry<Character, Integer> entry:Map.entrySet()){
            count+=entry.getValue();
        }
        return count;
    }
}
