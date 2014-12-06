package selfbyselfwest;

import selfbyselfwest.recognition.parser.RecognitionResults;

import java.util.HashMap;
import java.util.Map;

public class TrackedUser {
    final long userId;
    /**
     * true means it was recognized and we identified a logo
     */
    final Map<String, Boolean> trackedImageUrls = new HashMap<String, Boolean>();

    public TrackedUser(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public boolean imgRecognizedAlready(String imgurl) {
        return trackedImageUrls.containsKey(imgurl);
    }
    public void markAsRecognized(String imgurl, boolean b){
        trackedImageUrls.put(imgurl,b);
    }
    public boolean hasBeenSuccesfullyRecognized(String imgurl){
        return Boolean.TRUE.equals(trackedImageUrls.get(imgurl));
    }

}
