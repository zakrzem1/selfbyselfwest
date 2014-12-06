package selfbyselfwest.recognition;

//import com.ning.http.client.AsyncHttpClient;
//import com.ning.http.client.Response;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.IOException;
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

//    public RecognitionResult searchWithHttpClient()
//    Gson gson = new Gson();
//
    public HttpEntity searchWithHttpClient(FileBody imagePart) throws ExecutionException, InterruptedException {

//        (token, imageData));
//        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entity.addPart("number", new StringBody("5555555555"));
//        entity.addPart("clip", new StringBody("rickroll"));
//        File fileToUpload = new File(filePath);
//        FileBody fileBody = new FileBody(fileToUpload, "application/octet-stream");
//        entity.addPart("upload_file", fileBody);
//        entity.addPart("tos", new StringBody("agree"));
//
//        HttpPost httpPost = new HttpPost("http://some-web-site");
//        httpPost.setEntity(entity);
//        HttpResponse response = httpClient.execute(httpPost);
//        HttpEntity result = response.getEntity();


        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("https://search.craftar.net" +
                    "/v1/search");

            //FileBody imagePart = new FileBody(new File(args[0]));
            StringBody tokenPart = new StringBody(token, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("token", tokenPart)
                    .addPart("image", imagePart)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
                System.out.println("==== resEntity ====");
                System.out.println(resEntity);
                System.out.println("==== /resEntity ====");
                return resEntity;
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                httpclient.close();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}
