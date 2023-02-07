package root.general;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileOperationsTest {
    private static FileOperations f;
    @Test
    public void readFile() {
        f=new FileOperations();
        byte[] arr=f.readFile("TestAB.txt");
        Assert.assertTrue(arr.length>0);
        Assert.assertArrayEquals(arr,"abaaba".getBytes());
        Assert.assertThrows(RuntimeException.class,()->{f.readFile("missing.txt");});
    }

    @Test
    public void compareFiles() {
        f=new FileOperations();
        Assert.assertTrue(f.compareFiles("MultiA.txt","MultiACopy.txt"));
        Assert.assertFalse(f.compareFiles("MultiA.txt","As.txt"));
    }
}