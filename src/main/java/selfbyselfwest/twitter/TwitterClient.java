package selfbyselfwest.twitter;

import selfbyselfwest.UserTracker;
import selfbyselfwest.recognition.parser.RecognitionResults;
import twitter4j.*;

import java.util.*;

public class TwitterClient {

    private final UserTracker userTracker;
    private final Twitter twitter;

    public TwitterClient(UserTracker userTracker, Twitter twitter ) {
        this.userTracker = userTracker;
        this.twitter = twitter;
    }

    public Collection<String> getFreshPhotosUrls(long userId) throws TwitterException {
        Collection<String> photos = getPhotosUrls(userId); //initially all
        Iterator<String> it = photos.iterator();
        while (it.hasNext()) {
            String imgurl = it.next();
            if (userTracker.imgRecognizedAlready(userId, imgurl)) {
                it.remove();
            }
        }
        return photos;
        // mediaEntities=[MediaEntityJSONImpl{id=541085771021942784, url=http://t.co/aby1XwuulG,
        // mediaURL=http://pbs.twimg.com/media/B4JSpFbIAAAaf6u.jpg, mediaURLHttps=https://pbs.twimg.com/media/B4JSpFbIAAAaf6u.jpg, expandedURL=http://twitter.com/johndoe0798/status/541085772288655360/photo/1, displayURL='pic.twitter.com/aby1XwuulG', sizes={0=Size{width=150, height=150, resize=101}, 1=Size{width=223, height=249, resize=100}, 2=Size{width=223, height=249, resize=100}, 3=Size{width=223, height=249, resize=100}}, type=photo}]
    }

    private Collection<String> getPhotosUrls(long userId) throws TwitterException {

        List<Status> statuses = twitter.getUserTimeline(userId);
        System.out.println("Showing timeline.");
        Collection<String> allPhotosFromAllTweets = new LinkedList<String>();
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                    status.getText());
            System.out.println(status);
            allPhotosFromAllTweets.addAll(collectPhotosFromTweet(status.getMediaEntities()));
        }
        return allPhotosFromAllTweets;
    }

    private Collection<String> collectPhotosFromTweet(MediaEntity[] mediaEntities) {
        if (mediaEntities == null || mediaEntities.length < 1) {
            return Collections.emptyList();
        }
        List<String> lst = new LinkedList<String>();
        for (MediaEntity lnk : mediaEntities) {
            if ("photo".equals(lnk.getType())) {
                lst.add(lnk.getMediaURL());
            }
        }
        return lst;
    }

    public void retweet(long userId, RecognitionResults result) {
        System.out.println("IGNORING retweet");
    }
}
