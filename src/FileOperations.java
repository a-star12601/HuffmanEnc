import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class FileOperations {
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
