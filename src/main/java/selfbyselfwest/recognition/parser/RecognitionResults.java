package selfbyselfwest.recognition.parser;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecognitionResults {

    public static final RecognitionResults EMPTY = new RecognitionResults();
    //    {
    //        "search_time": 156,
    //            "results": [
    //        {
    //            "item": {
    //            "url": "http://thedailyshow.cc.com/",
    //                    "content": null,
    //                    "uuid": "150888159a5947ada62853e1a319a406",
    //                    "name": "dailyshow",
    //                    "custom": "https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json"
    //        },
    //            "image": {
    //            "thumb_120": "https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe",
    //                    "uuid": "08bcd55b6fa34da1bfb94b18b9999b4e"
    //        },
    //            "score": 36
    //        }
    //        ]
    //    }
    @SerializedName("search_time")
    private transient int searchTime;

    private List<RecognitionResult> results= new ArrayList<RecognitionResult>();

    public List<RecognitionResult> getResults() {
        return results!=null ? results: Collections.<RecognitionResult>emptyList();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean isSuccess() {
        return !getResults().isEmpty();
    }

    public RecognitionResults() {
    }

    public RecognitionResults(List<RecognitionResult> results) {
        this.results = results;
    }
}

