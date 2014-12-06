package selfbyselfwest;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.content.ByteArrayBody;
import selfbyselfwest.recognition.RecognitionSearch;
import selfbyselfwest.recognition.parser.RecognitionResults;
import selfbyselfwest.twitter.TweetedPhoto;
import selfbyselfwest.twitter.TwitterClient;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class App implements Runnable {
    final RecognitionSearch recognitionSearch;
    final TwitterClient twitterClient;
    final UserTracker userTracker;

    private long sleepIntervalMillis = 5000;
    private Map<String, String> ignoreMap;

    public App(RecognitionSearch recognitionSearch, TwitterClient twitterClient, UserTracker userTracker) {
        this.recognitionSearch = recognitionSearch;
        this.twitterClient = twitterClient;
        this.userTracker = userTracker;
        this.ignoreMap = new HashMap<String, String>();
        ignoreMap.put("/media/B4LmBXQIEAA6Ux3.jpg","Y");
        ignoreMap.put("/media/B4Ld_5_CUAAXLjL.jpg","Y");
        ignoreMap.put("/media/B4JSpFbIAAAaf6u.jpg","Y");
    }

    static App getInstance() {
        Twitter twitter = TwitterFactory.getSingleton();
        RecognitionSearch recognitionSearch = new RecognitionSearch();
        UserTracker userTracker = new UserTracker();
        TwitterClient twitterClient = new TwitterClient(userTracker, twitter);
        return new App(recognitionSearch, twitterClient, userTracker);
    }

    public static void main(String[] args) {
        App app = getInstance();
        (new Thread(app)).start();
    }

    @Override
    public void run() {
        while (true) {
            processAllTrackedUsers();
            try {
                Thread.sleep(sleepIntervalMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void processAllTrackedUsers() {
        Collection<TrackedUser> trackedUsers = userTracker.getTrackedUsers();
        for (TrackedUser trackedUser : trackedUsers) {
            try {
                Collection<TweetedPhoto> freshPhotosUrls = twitterClient.getFreshPhotosUrls(trackedUser.getUserId());
                for (TweetedPhoto tweetedPhoto : freshPhotosUrls) {
                    String urlStr = tweetedPhoto.getPhotoUrl();
                    long tweetId = tweetedPhoto.getTweetId();
                    //byte[] imageData = IOUtils.toByteArray(new FileInputStream(args[0]));
                    URL url = null;
                    try {
                        url = new URL(urlStr); //FIXME add :medium somewhere around here
                    } catch (MalformedURLException e) {
                        System.err.println("Invalid url: " + urlStr);
                        continue;
                    }
                    byte[] body = retrieveBin(url);
                    if (body == null) {
                        System.out.println("Couldn't retrieve image from " + urlStr);
                        continue;
                    }
                    try {
                        if (!isInIgnoreList(url.getFile())) {

                            RecognitionResults result =
                                    recognitionSearch.searchWithHttpClient(new ByteArrayBody(body, url.getFile()));
                            System.out.println("[App][process] received a recognition result: "+result);
                            trackedUser.markAsRecognized(urlStr, result.isSuccess()); //, check result first ??
                            twitterClient.retweetAndAnswer(trackedUser.getUserId(), result, tweetedPhoto);

                        } else {
                            System.err.println(
                                    "[App][processTrackedUsers] Ignoring recognition search for photo [" + url.getFile()
                                            + "] (fullUrl = " + urlStr + ") and user " + trackedUser.getUserId());
                        }
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

    private boolean isInIgnoreList(String file) {
        return ignoreMap.containsKey(file);
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
