package selfbyselfwest;

import com.google.common.base.Optional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserTracker {
    public static long USERID_JOHNDOE = 2907296770L;
    public static long USERID_DAILYSHOWFAN = 2906830433L;
    final Map<Long, TrackedUser> trackedUsers = new HashMap<Long, TrackedUser>();

    public UserTracker() {
        ensureTrackedUser(USERID_JOHNDOE);
        ensureTrackedUser(USERID_DAILYSHOWFAN);

    }

    public Collection<TrackedUser> getTrackedUsers() {
        return trackedUsers.values();
    }

    public Optional<TrackedUser> getTrackedUser(long userId) {
        return Optional.fromNullable(trackedUsers.get(userId)); //<TrackedUser>
    }

    public boolean imgRecognizedAlready(long userId, String imgurl) {
        if(!ensureTrackedUser(userId)){
            return false;
        }else return trackedUsers.get(userId).imgRecognizedAlready(imgurl);

    }

    /**
     * true if user already tracked
     */
    private boolean ensureTrackedUser(long userId) {
        if(!trackedUsers.containsKey(userId)) {
            trackedUsers.put(USERID_JOHNDOE, new TrackedUser(USERID_JOHNDOE));
            return false;
        }
        return true;
    }
}
