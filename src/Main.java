import compression.HuffmanEncoding;
import decompression.HuffmanDecoding;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * Main class.
 */
public class Main implements StandardUI{

    /**
     * variable storing original filename.
     */
    String filename="";
    /**
     * variable storing compressed filename.
     */
    String compressed="";

    /**
     * Main method
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Main m=new Main();
        m.readFileName();
        m.encode();
        m.decode();

    }


    @Override
    public void readFileName() {
        System.out.println("Enter Filename to be compressed:");
        Scanner s= new Scanner(System.in);
        filename=s.nextLine();
    }

    @Override
    public void encode() {
        HuffmanEncoding enc=new HuffmanEncoding();
        Instant inst1 = Instant.now();
//        System.out.println("Creating Frequency Map...");
        enc.initialiseMap(filename);
//        System.out.println("Creating Huffman Tree...");
        enc.initialiseTree();
//        System.out.println("Creating HashTable...");
        enc.generateTreeMap();
//        System.out.println("Writing Map to File...");
        enc.storeMap();
        try {
//            System.out.println("Compressing to File...");
            enc.encodeText(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Instant inst2 = Instant.now();
        System.out.println("Time Taken for Compression: "+ Duration.between(inst1, inst2).toString());
        compressed="Compressed.txt";
        enc.compressionStats(filename,compressed);
    }

    @Override
    public void decode() {
        HuffmanDecoding decode=new HuffmanDecoding();
        Instant inst1 = Instant.now();
//        System.out.println("Reading Frequency Map...");
        decode.initialiseMap(compressed);
        decode.getCount();
//        System.out.println("Creating Huffman Tree...");
        decode.initialiseTree();
//        decode.GenerateTreeMap();
//        System.out.println("Decompressing...");
        decode.decodeText(compressed);
        Instant inst2 = Instant.now();
        System.out.println("Time Taken for Decompression: "+ Duration.between(inst1, inst2).toString());
        System.out.println("Comparing Files...");
        decode.compareFiles(filename,"Decompressed.txt");
    }
}