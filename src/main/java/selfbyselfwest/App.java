package selfbyselfwest;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import selfbyselfwest.recognition.RecognitionSearch;
import selfbyselfwest.recognition.parser.RecognitionResults;
import selfbyselfwest.twitter.TwitterClient;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class App implements Runnable {
    final RecognitionSearch recognitionSearch;
    final TwitterClient twitterClient;
    final UserTracker userTracker;

    private long sleepIntervalMillis = 5000;

    public App(RecognitionSearch recognitionSearch, TwitterClient twitterClient, UserTracker userTracker) {
        this.recognitionSearch = recognitionSearch;
        this.twitterClient = twitterClient;
        this.userTracker = userTracker;
    }

    public static void main(String[] args) {
        Twitter twitter = TwitterFactory.getSingleton();
        RecognitionSearch recognitionSearch = new RecognitionSearch();
        UserTracker userTracker = new UserTracker();
        TwitterClient twitterClient = new TwitterClient(userTracker, twitter);
        (new Thread(new App(recognitionSearch, twitterClient, userTracker))).start();
    }

    @Override
    public void run() {
        while (true) {
            processAllTrackedUsers();
//            try {
//                Thread.sleep(sleepIntervalMillis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void processAllTrackedUsers() {
        Collection<TrackedUser> trackedUsers = userTracker.getTrackedUsers();
        for (TrackedUser trackedUser : trackedUsers) {
            try {
                Collection<String> freshPhotosUrls = twitterClient.getFreshPhotosUrls(trackedUser.getUserId());
                for (String urlStr : freshPhotosUrls) {
                    //byte[] imageData = IOUtils.toByteArray(new FileInputStream(args[0]));
                    URL url = null;
                    try {
                        url = new URL(urlStr);
                    } catch (MalformedURLException e) {
                        System.out.println("Invalid url: "+urlStr);
                        continue;
                    }
                    byte[] body = retrieveBin(url);
                    if(body==null){
                        System.out.println("Couldn't retrieve image from "+urlStr);
                        continue;
                    }
                    try {
                        RecognitionResults result =
                                recognitionSearch.searchWithHttpClient(new ByteArrayBody(body, url.getFile()));
                        trackedUser.markAsRecognized(urlStr); //, result ??
                        twitterClient.retweet(trackedUser.getUserId(), result);
                        System.out.println(result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } catch (TwitterException e) {
                System.err.println("Can't retrieve photos from twitter ");
                e.printStackTrace();
            }
        }
    }

    private byte[] retrieveBin(URL url) {
        InputStream is = null;
        try {
            is = url.openStream();
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", url != null ? url.toExternalForm() : "null",
                    e.getMessage());
            e.printStackTrace();
            // Perform any other exception handling that's appropriate.
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
