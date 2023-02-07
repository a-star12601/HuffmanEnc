package root;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainTest {
    private static Main m;
    @BeforeClass
    public static void setup(){
        m=new Main();
    }
    @Test
    public void checkFilesEmpty() {
        assertFalse("File is Empty",m.CheckFileNotEmpty("Empty.txt"));
        assertTrue("File is Not Empty",m.CheckFileNotEmpty("Ascii.txt"));
    }

    @Test
    public void checkFileExists() {
        assertTrue("File Exists",m.CheckFileExists("Empty.txt"));
        assertFalse("File Not Exists",m.CheckFileExists("Ascii2.txt"));
    }
/*
    @Test
    public void encode() {
        assertTrue(m.encode("MultiA.txt"));
    }

    @Test
    public void decode() {
        m.encode("MultiA.txt");
        assertTrue(m.decode("MultiA.txt","Compressed.txt"));
    }

 */
}