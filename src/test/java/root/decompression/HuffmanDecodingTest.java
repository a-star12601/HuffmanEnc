package root.decompression;

import org.junit.Assert;
import org.junit.Test;
import root.compression.*;
import root.general.Node;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HuffmanDecodingTest {

    @Test
    public void TestInitialiseMap() throws FileNotFoundException {
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',21);
        map.put('b',29);
        map.put('c',22);
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed2.txt");
        assertEquals(dec.map,map);
    }

    @Test
    public void TestInitialiseTreeSingleNode() throws FileNotFoundException {
        Node tree=new Node(88,new Node('a',88),new Node('\0',0),0);
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("CompressedA.txt");
        dec.initialiseTree();
        assertTrue(MatchTrees(tree,dec.tree));

    }
    @Test
    public void TestInitialiseTreeMultiNode() throws FileNotFoundException {
        Node tree=new Node();
        tree.Left=new Node('b',29);
        tree.Right=new Node(43,new Node('a',21),new Node('c',22),1);
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed2.txt");
        dec.initialiseTree();
        assertTrue(MatchTrees(tree,dec.tree));

    }
    public void preorder(Node tree){
        if(tree!=null){
            System.out.println(tree.Char);
            preorder(tree.Left);
            preorder(tree.Right);
        }
    }

    @Test
    public void TestOverallDecode() throws FileNotFoundException {
        HuffmanEncoding enc=new HuffmanEncoding();
        enc.initialiseMap("test.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        enc.storeMap("Compressed.txt");
        enc.encodeText("test.txt");
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed.txt");
        dec.initialiseTree();
        dec.getCount();
        dec.decodeText("Compressed.txt");
        assertTrue(dec.compareFiles("test.txt","Decompressed.txt"));
    }

    @Test
    public void checkDecoding(){
        HuffmanDecoding dec=new HuffmanDecoding();
        List<Byte> bytes=new ArrayList<>();
        byte[] decompressed="abcbabcabcbabbcbcabcbacbabcbcabcabcabacbacbabcabacbcabbcbcaacbacbbacbacb".getBytes();
        for(byte i:decompressed)
            bytes.add(i);
        byte[] tempInt=new byte[]{-102,115,70,-26,-76,-36,-25,45,105,-53,113,-70,-42,90,-64};
        Node tree=new Node();
        tree.Left=new Node('b',0);
        tree.Right=new Node(0,new Node('a',0),new Node('c',0),1);
        List<Byte> eval=dec.decodingLogic(tempInt,tree,0,72);
        Assert.assertEquals(bytes,eval);
    }



    public boolean MatchTrees(Node expected, Node actual){
        if(expected==null && actual==null)
        {return true;}
//        if(expected.Char==actual.Char && expected.Freq==actual.Freq && expected.Height==actual.Height)
        if(expected.Char==actual.Char)
            return MatchTrees(expected.Left,actual.Left)&&MatchTrees(expected.Right,actual.Right);
        else return false;
    }
}