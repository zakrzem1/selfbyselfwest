package selfbyselfwest;

import org.apache.commons.io.IOUtils;
import selfbyselfwest.recognition.RecognitionResult;
import selfbyselfwest.recognition.RecognitionSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println(args[0]);
        try {
            byte[] imageData = IOUtils.toByteArray(new FileInputStream(args[0]));
            RecognitionResult result = new RecognitionSearch().search(imageData);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
