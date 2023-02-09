package root.compression;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import root.general.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HuffmanEncodingTest {
    private static HuffmanEncoding enc;
    @Before
    public void setup(){

    }

    @Test
    public void testInitialiseMap() {
        enc=new HuffmanEncoding();
        enc.initialiseMap("MultiA.txt");
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',88);
        Assert.assertEquals("Map Doesn't Match", enc.map, map);
    }

    @Test
    public void testInitialiseTreeForSingleNode() {
        enc=new HuffmanEncoding();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',88);
        enc.tree=enc.initialiseTree(map);
        Node tree=new Node();
        tree.Left=new Node('a',88);
        tree.Freq=88;
        tree.Right=new Node();
        //System.out.println(enc.tree.Left);
        Assert.assertTrue(MatchTrees(enc.tree, tree));

    }
    @Test
    public void testInitialiseTreeForMultipleNodes() {
        enc=new HuffmanEncoding();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',4);
        map.put('b',2);
        enc.tree=enc.initialiseTree(map);
        Node tree=new Node();
        tree.Left=new Node('b',2);
        tree.Freq=6;
        tree.Right=new Node('a',4);
        //System.out.println(enc.tree.Left);
        Assert.assertTrue(MatchTrees(enc.tree, tree));

    }


    @Test
    public void testGenerateTreeMap() {
        enc=new HuffmanEncoding();
        Node tree=new Node(72,new Node('b',29),new Node(43,new Node('a',21),new Node('c',22),1),2);
        enc.generateTreeMap(tree);
        HashMap<Character,String > hash=new HashMap<>();
        hash.put('b',"0");
        hash.put('a',"10");
        hash.put('c',"11");
        Assert.assertEquals("Hashmaps not Equal", enc.hash, hash);
    }
    @Test
    public void checkEncoding(){
        enc=new HuffmanEncoding();
        HashMap<Character,String> map=new HashMap<>();
        map.put('a',"10");
        map.put('b',"0");
        map.put('c',"11");
        List<Byte> bytes=new ArrayList<>();
        byte[] tempInt=new byte[]{-102,115,70,-26,-76,-36,-25,45,105,-53,113,-70,-42,90,-64};
        for(byte i:tempInt)
            bytes.add(i);
        List<Byte> eval=enc.encodingLogic("abcbabcabcbabbcbcabcbacbabcbcabcabcabacbacbabcabacbcabbcbcaacbacbbacbacb".getBytes(),map);
        Assert.assertEquals(bytes,eval);
    }
    @Test
    public void checkOverallEncode() throws FileNotFoundException {
        enc=new HuffmanEncoding();
        enc.initialiseMap("pg101.txt");
        enc.tree=enc.initialiseTree(enc.map);
        enc.generateTreeMap(enc.tree);
        enc.storeMap("Compressed.txt");
        enc.encodeText("pg101.txt");
        Assert.assertTrue(new File("Compressed.txt").exists());

    }


    public boolean MatchTrees(Node expected,Node actual){
        if(expected==null && actual==null)
        {return true;}
        if(expected.Char==actual.Char)
        return MatchTrees(expected.Left,actual.Left)&&MatchTrees(expected.Right,actual.Right);
        else return false;
    }
    public void preorder(Node tree){
        if(tree!=null){
            System.out.println(tree.Char);
            preorder(tree.Left);
            preorder(tree.Right);
        }
    }
}