import java.io.*;
import java.util.*;

public class HuffmanDecoding extends FileOperations implements TreeGenerator,DecoderInterface{

    HashMap<Character,Integer> map;
    Node Tree;
    long count;
    long mapsize;
//    @Override
//    public HashMap<Character, Integer> InitialiseMap(String filename){
//        HashMap<Character, Integer> map = new HashMap<>();
//        try {
//            File mapfile = new File(filename);
//            Scanner s = new Scanner(mapfile);
//            while (s.hasNextLine()) {
//                String line = s.nextLine();
//                if (line.substring(0, 2).equals("\\n")) {
//                    map.put('\n', Integer.parseInt(line.substring(3)));
//                } else
//                    map.put(line.charAt(0), Integer.parseInt(line.substring(2)));
//            }
//            s.close();
//        }
//        catch (Exception e){}
//            return map;
//    }

    @Override
    public void InitialiseMap(String filename) {
        try{
            //System.out.println(filename);
            byte[] b=ReadFile(filename);
            mapsize=Long.parseLong(filename.substring(0,filename.length()-4));
            byte[] b1=Arrays.copyOfRange(b,0,(int)mapsize);
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
//    public void InitialiseMap(String filename) {
//        try{
//            FileInputStream input=new FileInputStream(filename);
//            ObjectInputStream serial=new ObjectInputStream(input);
//            map=(HashMap<Character, Integer>) serial.readObject();
//            serial.close();
//            input.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

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
    public void GenerateTreeMap() {     }

    @Override
    public void SetBitsHash(Node Tree, String bits, HashMap<Character, String> FreqMap) {

    }

    @Override
    public void DecodeText(String filename) {
        try {
            //System.out.println("Decompressing");
            File decomp=new File("Decompressed.txt");
            FileOutputStream output= new FileOutputStream(decomp);
            byte[] arr = ReadFile(filename);
            //System.out.println(arr.length);
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
                output.write((byte)root.Char);
                chars++;
                if(chars==count){break;}
                root=Tree;
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void DecodeText(Node Tree, String filename,long count) {
//        try {
//            System.out.println("Decompressing");
//            File compressed=new File(filename);
//            FileInputStream input = new FileInputStream(compressed);
//            File decomp=new File("Decompressed.txt");
//            FileOutputStream output= new FileOutputStream(decomp);
//            byte[] arr = new byte[(int)compressed.length()+1];
//            input.read(arr);
//            input.close();
//            String CurrentByte;
//            int curbyte=0;
//            Node root=Tree;
//            byte b=arr[curbyte];
//            int chars=0;
//            CurrentByte=String.format("%8s", Integer.toBinaryString((b + 256) % 256))
//                    .replace(' ', '0');
//            while(curbyte<(int)compressed.length()){
//                //System.out.println("Out");
//                while(root.Left!=null && root.Right!=null){
//                    //System.out.println("In");
//                    if(CurrentByte.length()==0){
//                        b=arr[++curbyte];
//                        CurrentByte=String.format("%8s", Integer.toBinaryString((b + 256) % 256))
//                                .replace(' ', '0');
//                    }
//                    else if(CurrentByte.charAt(0)=='0') {
//                        CurrentByte = CurrentByte.substring(1);
//                        root = root.Left;
//                    }
//                    else if(CurrentByte.charAt(0)=='1') {
//                        CurrentByte = CurrentByte.substring(1);
//                        root = root.Right;
//                    }
//                }
//                output.write((byte)root.Char);
//                chars++;
//                if(chars==count-1){break;}
//                root=Tree;
//            }
//            //System.out.println(chars+" "+count);
//            output.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void getCount() {
        count=0;
        for(Map.Entry<Character, Integer> entry:map.entrySet()){
            count+=entry.getValue();
        }
    }

}
