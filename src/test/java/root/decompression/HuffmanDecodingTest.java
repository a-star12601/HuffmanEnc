package root.decompression;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import root.compression.*;
import root.general.FileOperations;
import root.general.Node;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    public void TestInitialiseMap() throws IOException, ClassNotFoundException {
        byte arr[]=new byte[]{50,52,49,10,-84,-19,0,5,115,114,0,17,106,97,118,97,46,117,116,105,108,46,72,97,115,104,77,97,112,5,7,-38,-63,-61,22,96,-47,3,0,2,70,0,10,108,111,97,100,70,97,99,116,111,114,73,0,9,116,104,114,101,115,104,111,108,100,120,112,63,64,0,0,0,0,0,12,119,8,0,0,0,16,0,0,0,3,115,114,0,19,106,97,118,97,46,108,97,110,103,46,67,104,97,114,97,99,116,101,114,52,-117,71,-39,107,26,38,120,2,0,1,67,0,5,118,97,108,117,101,120,112,0,97,115,114,0,17,106,97,118,97,46,108,97,110,103,46,73,110,116,101,103,101,114,18,-30,-96,-92,-9,-127,-121,56,2,0,1,73,0,5,118,97,108,117,101,120,114,0,16,106,97,118,97,46,108,97,110,103,46,78,117,109,98,101,114,-122,-84,-107,29,11,-108,-32,-117,2,0,0,120,112,0,0,0,21,115,113,0,126,0,2,0,98,115,113,0,126,0,4,0,0,0,29,115,113,0,126,0,2,0,99,115,113,0,126,0,4,0,0,0,22,120};
        HashMap<Character,Integer> map=new HashMap<>();
        map.put('a',21);
        map.put('b',29);
        map.put('c',22);
        HuffmanDecoding dec=new HuffmanDecoding();
        HashMap<Character,Integer> ActualMap=dec.initialiseMap(arr);
        assertEquals(ActualMap,map);
    }

    @Test
    public void TestMapNullFile(){
        byte[] arr=null;
        HuffmanDecoding dec=new HuffmanDecoding();
        assertThrows(RuntimeException.class,()->{HashMap<Character,Integer> ActualMap=dec.initialiseMap(arr);});
    }
    @Test
    public void TestMapEmptyFile(){
        byte[] arr=new byte[0];
        HuffmanDecoding dec=new HuffmanDecoding();
        assertThrows(RuntimeException.class,()->{HashMap<Character,Integer> ActualMap=dec.initialiseMap(arr);});
    }

    @Test
    public void TestInitialiseTreeNullNode(){
        HuffmanDecoding dec=new HuffmanDecoding();
        Node tree=new Node();
        assertThrows(RuntimeException.class,()->dec.initialiseTree(null));
    }
    @Test
    public void TestInitialiseTreeEmptyMap(){
        HuffmanDecoding dec=new HuffmanDecoding();
        Node tree=new Node();
        assertThrows(RuntimeException.class,()->dec.initialiseTree(new HashMap<Character,Integer>()));
    }
    @Test
    public void TestInitialiseTreeSingleNode(){
        HuffmanDecoding dec=new HuffmanDecoding();
        Node tree=new Node(88,new Node('a',88),new Node('\0',0),0);
        HashMap<Character,Integer > map=new HashMap<>();
        map.put('a',88);
        Node ActualTree=dec.initialiseTree(map);
        assertTrue(MatchTrees(tree,ActualTree));
    }
    @Test
    public void TestInitialiseTreeMultiNode(){
        Node tree=new Node(72,new Node('b',29),new Node(43,new Node('a',21),new Node('c',22),1),2);
        HuffmanDecoding dec=new HuffmanDecoding();
        HashMap<Character,Integer > map=new HashMap<>();
        map.put('a',21);
        map.put('b',29);
        map.put('c',22);
        Node ActualTree=dec.initialiseTree(map);
        assertTrue(MatchTrees(tree,ActualTree));
    }
    @Test
    public void TestDummyTreeMap(){
        HuffmanDecoding dec=new HuffmanDecoding();
        assertNull(dec.generateTreeMap(new Node()));
    }

    @Test
    public void TestGetMapSize(){
        HuffmanDecoding dec=new HuffmanDecoding();
        assertEquals(dec.getMapSize(),0);
    }

    public void preorder(Node tree){
        if(tree!=null){
            System.out.println(tree.Char);
            preorder(tree.Left);
            preorder(tree.Right);
        }
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