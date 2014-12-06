package selfbyselfwest.recognition;

import java.net.URL;

public class RecognitionResult {
    private String collectionName;

    private URL targetUrl;

    private URL templateImgUrl;

    public RecognitionResult() {
    }

    public URL getTargetUrl(){
        return targetUrl;
    }

    public URL getTemplateImgUrl() {
        return templateImgUrl;
    }

    public void setTemplateImgUrl(URL templateImgUrl) {
        this.templateImgUrl = templateImgUrl;
    }

    @Override
    public String toString() {
        return "RecognitionResult{" +
                "collectionName='" + collectionName + '\'' +
                ", targetUrl=" + targetUrl +
                ", templateImgUrl=" + templateImgUrl +
                '}';
    }
}
