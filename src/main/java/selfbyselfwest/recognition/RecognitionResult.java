package selfbyselfwest.recognition;

import com.google.common.base.Optional;

import java.net.URL;

public class RecognitionResult {
    private RecognitionItem item;

    private ImageRef image;
    private int score;

    public Optional<String> getTargetUrl(){
        return item!=null ? Optional.fromNullable(item.url) : Optional.<String>absent();
    }

    /**
     *
     * @return currently we're holding link to SelfBySelfWest twitter feed
     */
    public Optional<String> getCustomData() {
        return item!=null ? Optional.fromNullable(item.custom) : Optional.<String>absent();
    }
    public Optional<String> getCollectionName(){
        return item!=null ? Optional.fromNullable(item.name) : Optional.<String>absent();
    }
    public Optional<String> getTemplateImgThumbnailUrl(){
        return image!=null ? Optional.fromNullable(image.getUrl()) : Optional.<String>absent();
    }
    @Override
    public String toString() {
        return "RecognitionResult{" +
                "collectionName='" + getCollectionName() + '\'' +
                ", targetUrl=" + getTargetUrl() +
                ", templateImgUrl=" + getTemplateImgThumbnailUrl() +
                ", customData=" +getCustomData() +
                '}';
    }
}
