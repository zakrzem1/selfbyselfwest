package selfbyselfwest.twitter;

/**
 *
 */
public class TweetedPhoto {
    final String photoUrl;
    final long tweetId;

    public TweetedPhoto(String photoUrl, long tweetId) {
        this.photoUrl = photoUrl;
        this.tweetId = tweetId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public long getTweetId() {
        return tweetId;
    }
}
