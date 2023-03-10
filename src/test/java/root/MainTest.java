package root;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import root.compression.HuffmanEncoding;
import root.decompression.HuffmanDecoding;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;

public class MainTest {
    private static Main m;
    @Mock
    HuffmanEncoding mockEnc;
    @Mock
    HuffmanDecoding mockDec;
    @Mock
    Main mock;
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


    @Test
    public void encodeTestIOException() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        doThrow(IOException.class).when(mockEnc).initialiseMap(any());
        doCallRealMethod().when(mock).encode("pg101.txt","HuffComp.txt",mockEnc);
        assertThrows(RuntimeException.class,()->mock.encode("pg101.txt","HuffComp.txt",mockEnc));
    }
    @Test
    public void encodeTestCNFException() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        doThrow(ClassNotFoundException.class).when(mockEnc).initialiseMap(any());
        doCallRealMethod().when(mock).encode("pg101.txt","HuffComp.txt",mockEnc);
        assertThrows(RuntimeException.class,()->mock.encode("pg101.txt","HuffComp.txt",mockEnc));
    }
    @Test
    public void encodeTest(){
        m.encode("pg101.txt","HuffComp.txt",new HuffmanEncoding());
        File f=new File("HuffComp.txt");
        assertTrue(f.exists()&&f.length()==3346239);
    }
    @Test
    public void decodeTest(){
        m.decode("pg101.txt","HuffComp.txt",new HuffmanDecoding());
        File f=new File("HuffComp.txt");
        assertTrue(f.exists()&&f.length()==3346239);
    }
    @Test
    public void decodeTestIOException() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        doThrow(IOException.class).when(mockDec).initialiseMap(any());
        doCallRealMethod().when(mock).decode("pg101.txt","HuffComp.txt",mockDec);
        assertThrows(RuntimeException.class,()->mock.decode("pg101.txt","HuffComp.txt",mockDec));
    }
    @Test
    public void decodeTestCNFException() throws IOException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        doThrow(ClassNotFoundException.class).when(mockDec).initialiseMap(any());
        doCallRealMethod().when(mock).decode("pg101.txt","HuffComp.txt",mockDec);
        assertThrows(RuntimeException.class,()->mock.decode("pg101.txt","HuffComp.txt",mockDec));
    }
}