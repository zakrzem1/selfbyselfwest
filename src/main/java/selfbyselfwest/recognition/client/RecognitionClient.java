package selfbyselfwest.recognition.client;

import org.apache.commons.io.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import selfbyselfwest.recognition.StatusAndBody;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RecognitionClient {
    private final String fullUrl;
    private final String token;

    public RecognitionClient(String token, String fullUrl) {
        this.fullUrl = fullUrl;
        this.token = token;
    }

    public StatusAndBody queryRecognitionService(ContentBody imagePart) throws ExecutionException,
            InterruptedException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(fullUrl);
            StringBody tokenPart = new StringBody(token, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity =
                    MultipartEntityBuilder.create().addPart("token", tokenPart).addPart("image", imagePart).build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                int status = response.getStatusLine().getStatusCode();
                System.out.println("status="+status);
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    System.out.println("==== resEntity ====");
                    System.out.println(resEntity);
                    System.out.println("==== /resEntity ====");
                    return new StatusAndBody(status, new String(EntityUtils.toByteArray(resEntity), Charsets.UTF_8));
                } else {
                    throw new RuntimeException("Can't reach response entity");
                }
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
