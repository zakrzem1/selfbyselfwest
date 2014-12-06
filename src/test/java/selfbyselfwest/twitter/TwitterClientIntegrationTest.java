package selfbyselfwest.twitter;

import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import selfbyselfwest.TrackedUser;
import selfbyselfwest.UserTracker;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import java.util.Collection;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class TwitterClientIntegrationTest {


    TwitterClient client;

    Twitter twitter;
    @Mock
    UserTracker userTracker;
    @Mock
    ResponseList<Status> twitterTimeline;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        twitter = TwitterFactory.getSingleton();
        client = new TwitterClient(userTracker, twitter);
    }

    @Test
    public void testGetFreshTweetsRealLife() throws Exception {
        Optional<TrackedUser> user = Optional.of(new TrackedUser(UserTracker.USERID_JOHNDOE));
        when(userTracker.getTrackedUser(eq(UserTracker.USERID_JOHNDOE))).thenReturn(user);

        Collection<TweetedPhoto> freshPhotos = client.getFreshPhotosUrls(UserTracker.USERID_JOHNDOE);
        Assert.assertFalse(freshPhotos.isEmpty());
    }


}