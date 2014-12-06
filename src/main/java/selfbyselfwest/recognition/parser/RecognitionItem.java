package selfbyselfwest.recognition.parser;

/**
 * {
 * //        "url": "http://thedailyshow.cc.com/",
 * //        "content": null,
 * //        "uuid": "150888159a5947ada62853e1a319a406",
 * //        "name": "dailyshow",
 * //        "custom": "https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json"
 * //    },
 */
public class RecognitionItem {
    String url;
    transient String content;
    String uuid;
    String name;
    String custom;
}
