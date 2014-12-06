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

import java.util.Collection;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class TwitterClientTest {


    TwitterClient client;
    @Mock
    Twitter twitter;
    @Mock
    UserTracker userTracker;
    @Mock
    ResponseList<Status> twitterTimeline;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //= TwitterFactory.getSingleton();


//        when(twitterTimeline.)
        client = new TwitterClient(userTracker, twitter);

    }

    @Test @Ignore
    public void testGetFreshTweetsWhenThereIsNone() throws Exception {
        when(twitter.getUserTimeline(123L)).thenReturn(twitterTimeline);
        when(userTracker.getTrackedUser(123L)).thenReturn(Optional.<TrackedUser>absent());
        when(userTracker.getTrackedUser(UserTracker.USERID_JOHNDOE)).thenReturn(Optional.<TrackedUser>absent());

        Collection<String> freshPhotos = client.getFreshPhotosUrls(UserTracker.USERID_JOHNDOE);
        Assert.assertTrue(freshPhotos.isEmpty());
    }

    @Test        @Ignore
    public void testGetFreshTweetsWhenThereIsNo() throws Exception {
        when(twitter.getUserTimeline(eq(UserTracker.USERID_JOHNDOE))).thenReturn(twitterTimeline);
        when(userTracker.getTrackedUser(eq(UserTracker.USERID_JOHNDOE))).thenReturn(Optional.<TrackedUser>absent());

        Collection<String> freshPhotos = client.getFreshPhotosUrls(UserTracker.USERID_JOHNDOE);
        Assert.assertTrue(freshPhotos.isEmpty());
    }

    @Test               @Ignore
    public void testGetFreshTweetsWhenThereIsNoUserTieline() throws Exception {
        when(twitter.getUserTimeline(eq(UserTracker.USERID_JOHNDOE))).thenReturn(null);
        when(userTracker.getTrackedUser(eq(UserTracker.USERID_JOHNDOE))).thenReturn(null);

        Collection<String> freshPhotos = client.getFreshPhotosUrls(UserTracker.USERID_JOHNDOE);
        Assert.assertTrue(freshPhotos.isEmpty());
    }
//    @Test
//    public void testGetFreshTweetsWhen(){
//        when(twitter.getUserTimeline(123L)).thenReturn(new ResponseList);
//
//    }



}