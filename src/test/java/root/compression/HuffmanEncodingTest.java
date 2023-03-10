package root.compression;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import root.Main;
import root.decompression.HuffmanDecoding;
import root.general.FileOperations;
import root.general.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class HuffmanEncodingTest {
    private static HuffmanEncoding enc;


    @Before
    public void setup(){

    }


    @Test
    public void testInitialiseMap() throws IOException, ClassNotFoundException {
        enc=new HuffmanEncoding();
        byte arr[]=new byte[]{97,97,97,97,97,97,97,97,97,97,97,97,97,97};
        HashMap<Character,Integer> ActualMap=enc.initialiseMap(arr);
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',14);
        Assert.assertEquals("Map Doesn't Match",ActualMap, map);
    }
    @Test
    public void TestMapNullFile(){
        byte[] arr=null;
        enc=new HuffmanEncoding();
        assertThrows(RuntimeException.class,()->{HashMap<Character,Integer> ActualMap=enc.initialiseMap(arr);});
    }
    @Test
    public void TestMapEmptyFile(){
        byte[] arr=new byte[0];
        enc=new HuffmanEncoding();
        assertThrows(RuntimeException.class,()->{HashMap<Character,Integer> ActualMap=enc.initialiseMap(arr);});
    }


    @Test
    public void testInitialiseTreeForSingleNode() {
        enc=new HuffmanEncoding();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',88);
        Node ActualTree=enc.initialiseTree(map);
        Node tree=new Node();
        tree.Left=new Node('a',88);
        tree.Freq=88;
        tree.Right=new Node();
        //System.out.println(enc.tree.Left);
        Assert.assertTrue(MatchTrees(ActualTree, tree));
    }
    @Test
    public void TestInitialiseTreeNullNode(){
        enc=new HuffmanEncoding();
        Node tree=new Node();
        assertThrows(RuntimeException.class,()->enc.initialiseTree(null));
    }
    @Test
    public void TestInitialiseTreeEmptyMap(){
        enc=new HuffmanEncoding();
        Node tree=new Node();
        assertThrows(RuntimeException.class,()->enc.initialiseTree(new HashMap<Character,Integer>()));
    }
    @Test
    public void testInitialiseTreeForMultipleNodes() {
        enc=new HuffmanEncoding();
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',4);
        map.put('b',2);
        Node ActualTree=enc.initialiseTree(map);
        Node tree=new Node();
        tree.Left=new Node('b',2);
        tree.Freq=6;
        tree.Right=new Node('a',4);
        //System.out.println(enc.tree.Left);
        Assert.assertTrue(MatchTrees(ActualTree, tree));
    }


    @Test
    public void testGenerateTreeMap() {
        enc=new HuffmanEncoding();
        Node tree=new Node(72,new Node('b',29),new Node(43,new Node('a',21),new Node('c',22),1),2);
        HashMap<Character,String> ActualHash=enc.generateTreeMap(tree);
        HashMap<Character,String > hash=new HashMap<>();
        hash.put('b',"0");
        hash.put('a',"10");
        hash.put('c',"11");
        Assert.assertEquals("Hashmaps not Equal", ActualHash, hash);
    }

//    @Test
//    public void checkOverallEncode() throws IOException {
//            HashMap<Character,Integer> map=enc.initialiseMap(inputBytes);
//            Node tree=enc.initialiseTree(map);
//            HashMap<Character,String> hash=enc.generateTreeMap(tree);
//    }

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