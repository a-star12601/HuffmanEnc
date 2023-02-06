package root.decompression;

import org.junit.Test;
import root.compression.*;
import root.general.Node;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class HuffmanDecodingTest {

    @Test
    public void TestInitialiseMap() throws FileNotFoundException {
        HuffmanEncoding enc=new HuffmanEncoding();
        enc.initialiseMap("test.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        enc.storeMap();
        enc.encodeText("test.txt");
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed.txt");
        assertEquals(dec.map,enc.map);
    }

    @Test
    public void TestInitialiseTreeSingleNode() throws FileNotFoundException {
        HuffmanEncoding enc=new HuffmanEncoding();
        enc.initialiseMap("MultiA.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        enc.storeMap();
        enc.encodeText("MultiA.txt");
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed.txt");
        dec.initialiseTree();
        assertTrue(MatchTrees(enc.tree,dec.tree));

    }
    @Test
    public void TestInitialiseTreeMultiNode() throws FileNotFoundException {
        HuffmanEncoding enc=new HuffmanEncoding();
        enc.initialiseMap("test.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        enc.storeMap();
        enc.encodeText("test.txt");
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed.txt");
        dec.initialiseTree();
        assertTrue(MatchTrees(enc.tree,dec.tree));

    }

    @Test
    public void TestDecode() throws FileNotFoundException {
        HuffmanEncoding enc=new HuffmanEncoding();
        enc.initialiseMap("test.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        enc.storeMap();
        enc.encodeText("test.txt");
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed.txt");
        dec.initialiseTree();
        dec.getCount();
        dec.decodeText("Compressed.txt");
        assertTrue(dec.compareFiles("test.txt","Decompressed.txt"));
    }


    public boolean MatchTrees(Node expected, Node actual){
        if(expected==null && actual==null)
        {return true;}
        if(expected.Char==actual.Char && expected.Freq==actual.Freq && expected.Height==actual.Height)
            return MatchTrees(expected.Left,actual.Left)&&MatchTrees(expected.Right,actual.Right);
        else return false;
    }
}