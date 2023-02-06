package root.compression;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import root.general.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

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
        enc.initialiseMap("MultiA.txt");
        enc.initialiseTree();
        Node tree=new Node();
        tree.Left=new Node('a',88);
        tree.Freq=88;
        tree.Right=new Node();
        //System.out.println(enc.tree.Left);
        preorder(enc.tree);
        preorder(tree);
        Assert.assertTrue(MatchTrees(enc.tree, tree));

    }
    @Test
    public void testInitialiseTreeForMultipleNodes() {
        enc=new HuffmanEncoding();
        enc.initialiseMap("TestAB.txt");
        enc.initialiseTree();
        Node tree=new Node();
        tree.Left=new Node('b',2);
        tree.Freq=6;
        tree.Right=new Node('a',4);
        //System.out.println(enc.tree.Left);
        preorder(enc.tree);
        preorder(tree);
        Assert.assertTrue(MatchTrees(enc.tree, tree));

    }


    @Test
    public void testGenerateTreeMap() {
        enc=new HuffmanEncoding();
        enc.initialiseMap("TestAB.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        HashMap<Character,String > hash=new HashMap<>();
        hash.put('b',"0");
        hash.put('a',"1");
        Assert.assertEquals("Hashmaps not Equal", enc.hash, hash);
    }

    @Test
    public void checkEncode() throws FileNotFoundException {
        enc=new HuffmanEncoding();
        enc.initialiseMap("pg101.txt");
        enc.initialiseTree();
        enc.generateTreeMap();
        enc.storeMap();
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