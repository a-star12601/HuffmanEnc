package root;

import root.compression.HuffmanEncoding;
import root.decompression.HuffmanDecoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * root.Main class.
 */
public class Main implements StandardUI{


    /**
     * root.Main method
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("Enter Filename to be compressed:");
        Scanner s= new Scanner(System.in);
        Main m=new Main();
        String filename=s.nextLine();
        if(m.CheckFileExists(filename) && m.CheckFileNotEmpty(filename)){
            m.encode(filename);
            m.decode(filename,"Compressed.txt");
        } else if (!m.CheckFileExists(filename)) {
            System.out.println("File Doesn't Exist!!");
        }
        else if(!m.CheckFileNotEmpty(filename)){
            System.out.println("File is empty!!");
        }
    }

    public boolean CheckFileNotEmpty(String filename){
        File f=new File(filename);
        return f.length()!=0;
    }
    public boolean CheckFileExists(String filename){
        File f=new File(filename);
        return f.exists();
    }
    @Override
    public boolean encode(String filename) {
        HuffmanEncoding enc=new HuffmanEncoding();
        Instant inst1 = Instant.now();
//        System.out.println("Creating Frequency Map...");
        enc.initialiseMap(filename);
//        System.out.println("Creating Huffman Tree...");
        enc.tree=enc.initialiseTree(enc.map);
//        System.out.println("Creating HashTable...");
        enc.generateTreeMap(enc.tree);
//        System.out.println("Writing Map to File...");
        enc.storeMap("Compressed.txt");
        try {
//            System.out.println("Compressing to File...");
            enc.encodeText(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Instant inst2 = Instant.now();
        System.out.println("Time Taken for Compression: "+ Duration.between(inst1, inst2).toString());
        String compressed="Compressed.txt";
        enc.compressionStats(filename,compressed);
        return true;
    }

    @Override
    public boolean decode(String filename,String compressed) {
        HuffmanDecoding decode=new HuffmanDecoding();
        Instant inst1 = Instant.now();
//        System.out.println("Reading Frequency Map...");
        decode.initialiseMap(compressed);
        decode.getCount();
//        System.out.println("Creating Huffman Tree...");
        decode.tree=decode.initialiseTree(decode.map);
//        decode.GenerateTreeMap();
//        System.out.println("Decompressing...");
        decode.decodeText(compressed);
        Instant inst2 = Instant.now();
        System.out.println("Time Taken for Decompression: "+ Duration.between(inst1, inst2).toString());
        System.out.println("Comparing Files...");
        if(decode.compareFiles(filename,"Decompressed.txt")){
            System.out.println("Files Matched");
        }
        else {
            System.out.println("Files Mismatched");
        }
            return true;
    }
}