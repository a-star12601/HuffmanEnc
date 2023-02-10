package root;

import root.compression.HuffmanEncoding;
import root.compression.TreeEncoder;
import root.decompression.HuffmanDecoding;
import root.decompression.TreeDecoder;
import root.general.FileOperations;
import root.general.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * root.Main class.
 */
public class Main{


    /**
     * root.Main method
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);
        Main m=new Main();
        System.out.println("Enter Filename to be compressed:");
        String filename=s.nextLine();
        System.out.println("Enter Compressed Filename:");
        String compressed=s.nextLine();
        if(m.CheckFileExists(filename) && m.CheckFileNotEmpty(filename)){
            m.encode(filename,compressed,new HuffmanEncoding());
            m.decode(filename,compressed,new HuffmanDecoding());
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
    public void encode(String filename,String compressed,HuffmanEncoding enc){
        //HuffmanEncoding enc=new HuffmanEncoding();
        try {
            FileOperations f=new FileOperations();
            Instant inst1 = Instant.now();
    //      System.out.println("Creating Frequency Map...");
            byte[] inputBytes=f.readFile(filename);
            HashMap<Character,Integer> map=enc.initialiseMap(inputBytes);
    //      System.out.println("Creating Huffman Tree...");
            Node tree=enc.initialiseTree(map);
    //      System.out.println("Creating HashTable...");
            HashMap<Character,String> hash=enc.generateTreeMap(tree);
            TreeEncoder encoder=new TreeEncoder();
    //      System.out.println("Writing Map to File...");
            List<Byte> encodedList= encoder.encodingLogic(inputBytes,hash);
//          System.out.println("Compressing to File...");
            byte[] headerContent=encoder.storeMap(map);
            byte[] encodedBytes=f.byteFromByteList(encodedList);
            f.writeToFile(compressed,false,headerContent);
            f.writeToFile(compressed,true,encodedBytes);
            Instant inst2 = Instant.now();
            System.out.println("Time Taken for Compression: "+ Duration.between(inst1, inst2).toString());
            f.compressionStats(filename,compressed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void decode(String filename,String compressed,HuffmanDecoding dec) {
        try {
            FileOperations f=new FileOperations();
            Instant inst1 = Instant.now();
    //        System.out.println("Reading Frequency Map...");
            byte[] compressBytes=f.readFile(compressed);
            HashMap<Character,Integer> map=dec.initialiseMap(compressBytes);
//            System.out.println("Creating Huffman Tree...");
            Node tree=dec.initialiseTree(map);
    //        decode.GenerateTreeMap();
    //        System.out.println("Decompressing...");
            TreeDecoder decoder=new TreeDecoder();
            List<Byte> decodedList=decoder.decodingLogic(compressBytes,tree,dec.getMapSize(),decoder.getCount(map));
            byte[] exportBytes=f.byteFromByteList(decodedList);
            f.writeToFile("DEC"+filename,false,exportBytes);
            Instant inst2 = Instant.now();
            System.out.println("Time Taken for Decompression: "+ Duration.between(inst1, inst2).toString());
            System.out.println("Comparing Files...");
            if(f.compareFiles(filename,"DEC"+filename)){
                System.out.println("Files Matched");
            }
            else {
                System.out.println("Files Mismatched");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        }
}