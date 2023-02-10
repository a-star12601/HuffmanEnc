package root.general;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * The Class which provides standard file operations.
 */
public class FileOperations {
    /**
     * Read file using filename and returns a byte array.
     *
     * @param filename Name of the file
     * @return byte array containing file contents
     */
    public byte[] readFile(String filename) throws IOException{
        File file = new File(filename);
        FileInputStream input = null;
        byte[] arr=new byte[0];
        input = new FileInputStream(file);
        arr = new byte[(int) file.length()];
        input.read(arr);
        input.close();
        return arr;
    }

    /**
     * Compare two files and checks whether they match or not.
     *
     * @param file1 Name of 1st file
     * @param file2 Name of 2nd file
     */
    public boolean compareFiles(String file1, String file2){
        try {
            byte[] arr1 = readFile(file1);
            byte[] arr2= readFile(file2);
            if(Arrays.equals(arr1,arr2)){
                return true;
            }
            else{
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compressionStats(String original, String compressed){
        File f1=new File(original);
        File f2=new File(compressed);
        System.out.println("Original File Size: "+ f1.length()+"bytes");
        System.out.println("Compressed File Size: "+ f2.length()+"bytes");
        float percentage=(float)(f1.length()- f2.length())*100/f1.length();
        System.out.println("Compression Ratio: "+ percentage+"%");
    }

    public byte[] byteFromByteList(List<Byte> bytes){
        byte[] exportBytes=new byte[bytes.size()];
        int i=0;
        for(Byte b:bytes){
            exportBytes[i++]=b.byteValue();
        }
        return exportBytes;
    }
    public void writeToFile(String filename,boolean appendMode,byte[] bytes) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename, appendMode);
        fout.write(bytes);
    }
}
