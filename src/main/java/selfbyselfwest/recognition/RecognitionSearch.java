package selfbyselfwest.recognition;

//import com.ning.http.client.AsyncHttpClient;
//import com.ning.http.client.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.WebTarget;
import java.util.concurrent.ExecutionException;

/**
 * curl https://search.craftar.net/v1/search -F "token=3e9d8f72fbb941dc" -F "image=@query.jpg"
 */
public class RecognitionSearch {
    private final String token;
    private final ResteasyWebTarget rtarget;

    public RecognitionSearch(){
        this("3e9d8f72fbb941dc","https://search.craftar.net");
    }
    public RecognitionSearch(String token, String serverUrl) {
        this.token = token;
        ResteasyClient client = new ResteasyClientBuilder().build();
        WebTarget target = client.target(serverUrl);
        rtarget = (ResteasyWebTarget)target;
    }

    public RecognitionResult search(byte[] imageData) throws ExecutionException, InterruptedException {
        //FIXME makes a file get attached in the post as a file upload
        RecognitionClient simple = rtarget.proxy(RecognitionClient.class);
        return simple.analyzeImg(new TokenAndImageForm(token, imageData));
    }


}
