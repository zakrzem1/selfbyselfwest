package selfbyselfwest.twitter;

import com.google.common.base.Optional;
import selfbyselfwest.UserTracker;
import selfbyselfwest.recognition.parser.RecognitionResult;
import selfbyselfwest.recognition.parser.RecognitionResults;
import twitter4j.*;

import java.util.*;

public class TwitterClient {

    private final UserTracker userTracker;
    private final Twitter twitter;
    public static final String TWEET_TXT_TEMPLATE =
            "Congrats! You just earned a point in %s contest. Keep up the good work";

    public TwitterClient(UserTracker userTracker, Twitter twitter) {
        this.userTracker = userTracker;
        this.twitter = twitter;
    }

    public Collection<TweetedPhoto> getFreshPhotosUrls(long userId) throws TwitterException {
        Collection<TweetedPhoto> photos = getPhotosUrls(userId); //initially all
        Iterator<TweetedPhoto> it = photos.iterator();
        while (it.hasNext()) {
            TweetedPhoto photoMeta = it.next();
            String imgurl = photoMeta.getPhotoUrl();
            if (userTracker.imgRecognizedAlready(userId, imgurl)) {
                it.remove();
            }
        }
        return photos;
        // mediaEntities=[MediaEntityJSONImpl{id=541085771021942784, url=http://t.co/aby1XwuulG,
        // mediaURL=http://pbs.twimg.com/media/B4JSpFbIAAAaf6u.jpg, mediaURLHttps=https://pbs.twimg.com/media/B4JSpFbIAAAaf6u.jpg, expandedURL=http://twitter.com/johndoe0798/status/541085772288655360/photo/1, displayURL='pic.twitter.com/aby1XwuulG', sizes={0=Size{width=150, height=150, resize=101}, 1=Size{width=223, height=249, resize=100}, 2=Size{width=223, height=249, resize=100}, 3=Size{width=223, height=249, resize=100}}, type=photo}]
    }

    private Collection<TweetedPhoto> getPhotosUrls(long userId) throws TwitterException {

        List<Status> statuses = twitter.getUserTimeline(userId);
        System.out.println("Showing timeline: " + statuses.size());
        Collection<TweetedPhoto> allPhotosFromAllTweets = new LinkedList<TweetedPhoto>();
        int i = 0;
        for (Status status : statuses) {
            System.out.println(i + " : " + status.getUser().getName() + " : " +
                    status.getText());
            //System.out.println(status);
            allPhotosFromAllTweets.addAll(collectPhotosFromTweet(status.getMediaEntities(), status.getId()));
        }
        return allPhotosFromAllTweets;
    }

    private Collection<TweetedPhoto> collectPhotosFromTweet(MediaEntity[] mediaEntities, long tweetId) {
        if (mediaEntities == null || mediaEntities.length < 1) {
            return Collections.emptyList();
        }
        List<TweetedPhoto> lst = new LinkedList<TweetedPhoto>();
        for (MediaEntity lnk : mediaEntities) {
            if ("photo".equals(lnk.getType())) {
                lst.add(new TweetedPhoto(lnk.getMediaURL(), tweetId));
            }
        }
        return lst;
    }

    public void retweetAndAnswer(long participantUserId, RecognitionResults result, TweetedPhoto tweetedPhoto) {
        retweet(tweetedPhoto.tweetId);
        answer(result, tweetedPhoto);
    }

    private void answer(RecognitionResults resObj, TweetedPhoto tweetedPhoto) {

        Optional<RecognitionResult> firstMatching = getFirstResultWithTemplatePresent(resObj);
        if (firstMatching.isPresent() && firstMatching.get().getCollectionName().isPresent()) {

            String text=String.format(TWEET_TXT_TEMPLATE, firstMatching.get().getCollectionName().get());
            StatusUpdate statusUpdate =
                    new StatusUpdate(text);
            statusUpdate.setInReplyToStatusId(tweetedPhoto.tweetId);
            //FIXME statusUpdate.setMedia();
            try {
                twitter.updateStatus(statusUpdate);
                System.out.println("[Twitter][answer] sent: "+text);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }

    private Optional<RecognitionResult> getFirstResultWithTemplatePresent(RecognitionResults resObj) {
        if (!resObj.getResults().isEmpty()) {
            for (RecognitionResult singleResult : resObj.getResults()) {
                if (singleResult.getTemplateImgThumbnailUrl().isPresent()) {
                    return Optional.of(singleResult);
                }
            }
        }
        return Optional.absent();
    }

    private void retweet(long tweetId) {
        try {
            twitter.retweetStatus(tweetId);
            System.out.println("[Twitter][retweet] retweeted "+tweetId);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
