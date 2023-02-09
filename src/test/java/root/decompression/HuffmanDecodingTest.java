package root.decompression;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import root.compression.*;
import root.general.Node;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HuffmanDecodingTest {

    @Mock
    HuffmanDecoding decMock;
    @Test
    public void TestInitialiseMap(){
        MockitoAnnotations.initMocks(this);
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',21);
        map.put('b',29);
        map.put('c',22);
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed2.txt");
        assertEquals(dec.map,map);
        doThrow(RuntimeException.class).when(decMock).readFile("Compressed2.txt");
        doCallRealMethod().when(decMock).initialiseMap("Compressed2.txt");
        assertThrows(RuntimeException.class,()-> decMock.initialiseMap("Compressed2.txt"));
    }

    @Test
    public void TestInitialiseTreeNullNode(){
        HuffmanDecoding dec=new HuffmanDecoding();
        Node tree=new Node();
        dec.tree=dec.initialiseTree(null);
        assertTrue(MatchTrees(tree,dec.tree));
    }
    @Test
    public void TestInitialiseTreeEmptyMap(){
        HuffmanDecoding dec=new HuffmanDecoding();
        Node tree=new Node();
        dec.tree=dec.initialiseTree(new HashMap<Character,Integer>());
        assertTrue(MatchTrees(tree,dec.tree));
    }
    @Test
    public void TestInitialiseTreeSingleNode(){
        HuffmanDecoding dec=new HuffmanDecoding();
        Node tree=new Node(88,new Node('a',88),new Node('\0',0),0);
        HashMap<Character,Integer > map=new HashMap<>();
        map.put('a',88);
        dec.tree=dec.initialiseTree(map);
        assertTrue(MatchTrees(tree,dec.tree));

    }
    @Test
    public void TestInitialiseTreeMultiNode(){
        Node tree=new Node(72,new Node('b',29),new Node(43,new Node('a',21),new Node('c',22),1),2);
        HuffmanDecoding dec=new HuffmanDecoding();
        HashMap<Character,Integer > map=new HashMap<>();
        map.put('a',21);
        map.put('b',29);
        map.put('c',22);
        dec.tree=dec.initialiseTree(map);
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
    public void TestOverallDecode(){
        HuffmanDecoding dec=new HuffmanDecoding();
        dec.initialiseMap("Compressed.txt");
        dec.tree=dec.initialiseTree(dec.map);
        dec.getCount();
        dec.decodeText("Compressed.txt");
        assertTrue(dec.compareFiles("pg101.txt","Decompressed.txt"));
    }

    @Test
    public void checkDecoding(){
        HuffmanDecoding dec=new HuffmanDecoding();
        List<Byte> bytes=new ArrayList<>();
        byte[] decompressed="abcbabcabcbabbcbcabcbacbabcbcabcabcabacbacbabcabacbcabbcbcaacbacbbacbacb".getBytes();
        for(byte i:decompressed)
            bytes.add(i);
        byte[] tempInt=new byte[]{-102,115,70,-26,-76,-36,-25,45,105,-53,113,-70,-42,90,-64};
        Node tree=new Node(72,new Node('b',29),new Node(43,new Node('a',21),new Node('c',22),1),2);
        List<Byte> eval=dec.decodingLogic(tempInt,tree,0,72);
        Assert.assertEquals(bytes,eval);
    }



    public boolean MatchTrees(Node expected, Node actual){
        if(expected==null && actual==null)
        {return true;}
        if(expected.Char==actual.Char && expected.Freq==actual.Freq && expected.Height==actual.Height)
//        if(expected.Char==actual.Char)
            return MatchTrees(expected.Left,actual.Left)&&MatchTrees(expected.Right,actual.Right);
        else return false;
    }
}