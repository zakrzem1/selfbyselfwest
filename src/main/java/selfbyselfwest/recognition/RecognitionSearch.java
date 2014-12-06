package selfbyselfwest.recognition;


import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import selfbyselfwest.recognition.client.RecognitionClient;
import selfbyselfwest.recognition.parser.RecognitionJsonParser;
import selfbyselfwest.recognition.parser.RecognitionResults;

import java.util.concurrent.ExecutionException;

/**
 * curl https://search.craftar.net/v1/search -F "token=3e9d8f72fbb941dc" -F "image=@query.jpg"
 */
public class RecognitionSearch {

    private final RecognitionJsonParser parser;
    private final RecognitionClient client;

    public RecognitionSearch(){
        this("3e9d8f72fbb941dc","https://search.craftar.net/v1/search");
    }
    public RecognitionSearch(String token, String fullUrl) {
        this.parser = new RecognitionJsonParser();
        this.client = new RecognitionClient(token, fullUrl);
    }

    public RecognitionResults searchWithHttpClient(ContentBody imagePart) throws ExecutionException,
            InterruptedException{
        StatusAndBody statusAndBody = client.queryRecognitionService(imagePart);
        if(statusAndBody.getStatus()==200){
            return parser.parse(statusAndBody.getBody());
        }else{
            return RecognitionResults.EMPTY;
        }

    }

}
