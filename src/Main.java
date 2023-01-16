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
        m.ReadFileName();
        m.Encode();
        m.Decode();

    }


    @Override
    public void ReadFileName() {
        System.out.println("Enter Filename to be compressed:");
        Scanner s= new Scanner(System.in);
        filename=s.nextLine();
    }

    @Override
    public void Encode() {
        HuffmanEncoding enc=new HuffmanEncoding();
        Instant inst1 = Instant.now();
        System.out.println("Creating Frequency Map...");
        enc.InitialiseMap(filename);
        System.out.println("Creating Huffman Tree...");
        enc.InitialiseTree();
        System.out.println("Creating HashTable...");
        enc.GenerateTreeMap();
        System.out.println("Writing Map to File...");
        enc.StoreMap();
        try {
            System.out.println("Compressing to File...");
            enc.EncodeText(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Instant inst2 = Instant.now();
        System.out.println("Time Taken for Compression: "+ Duration.between(inst1, inst2).toString());
        compressed=enc.mapsize+".txt";
    }

    @Override
    public void Decode() {
        HuffmanDecoding decode=new HuffmanDecoding();
        Instant inst1 = Instant.now();
        System.out.println("Reading Frequency Map...");
        decode.InitialiseMap(compressed);
        decode.getCount();
        System.out.println("Creating Huffman Tree...");
        decode.InitialiseTree();
        System.out.println("Decompressing...");
        decode.DecodeText(compressed);
        Instant inst2 = Instant.now();
        System.out.println("Time Taken for Decompression: "+ Duration.between(inst1, inst2).toString());
        System.out.println("Comparing Files...");
        decode.CompareFiles(filename,"Decompressed.txt");
    }
}