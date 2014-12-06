package selfbyselfwest.recognition;

import com.google.gson.annotations.SerializedName;

public class ImageRef {
//    {
        //        "thumb_120": "https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe",
        //        "uuid": "08bcd55b6fa34da1bfb94b18b9999b4e"
        //    },

    @SerializedName("thumb_120") String url;
    transient String uuid;

    public String getUrl() {
        return url;
    }
}
