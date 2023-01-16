import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

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
    public byte[] ReadFile(String filename){
        File file = new File(filename);
        FileInputStream input = null;
        byte[] arr;
        try {
            input = new FileInputStream(file);
            arr = new byte[(int) file.length()];
            input.read(arr);
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return arr;
    }

    /**
     * Compare two files and checks whether they match or not.
     *
     * @param file1 Name of 1st file
     * @param file2 Name of 2nd file
     */
    public void CompareFiles(String file1,String file2){
        byte[] arr1=ReadFile(file1);
        byte[] arr2=ReadFile(file2);
        if(Arrays.equals(arr1,arr2)){
            System.out.println("Files Matched");
        }
        else{
        System.out.println("Files Mismatched");
        }
    }


}
