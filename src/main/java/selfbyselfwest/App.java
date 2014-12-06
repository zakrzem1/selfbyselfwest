package selfbyselfwest;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.FileBody;
import selfbyselfwest.recognition.RecognitionResult;
import selfbyselfwest.recognition.RecognitionResults;
import selfbyselfwest.recognition.RecognitionSearch;

import java.io.File;
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
            //RecognitionResult result = new RecognitionSearch().search(imageData);

            RecognitionResults result = new RecognitionSearch().searchWithHttpClient(new FileBody(new File(args[0])));

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
