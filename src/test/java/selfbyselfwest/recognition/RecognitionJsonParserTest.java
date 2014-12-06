package selfbyselfwest.recognition;

import org.junit.Test;
import selfbyselfwest.recognition.parser.RecognitionJsonParser;
import selfbyselfwest.recognition.parser.RecognitionResult;
import selfbyselfwest.recognition.parser.RecognitionResults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecognitionJsonParserTest {
    RecognitionJsonParser parser = new RecognitionJsonParser();
//    static String JSON = "{\n" + "        \"item\": {\n" + "            \"url\": \"http://thedailyshow.cc.com/\",\n"
//            + "            \"content\": null,\n" + "            \"uuid\": \"150888159a5947ada62853e1a319a406\",\n"
//            + "            \"name\": \"dailyshow\",\n"
//            + "            \"custom\": \"https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json\"\n"
//            + "        },\n" + "        \"image\": {\n"
//            + "            \"thumb_120\": \"https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe\",\n"
//            + "            \"uuid\": \"08bcd55b6fa34da1bfb94b18b9999b4e\"\n" + "        },\n"
//            + "        \"score\": 36\n" + "    }";

    static String JSON_RESULTS = "{\"search_time\": 148, \"results\": [{\"item\": {\"url\": \"http://thedailyshow.cc.com/\", \"content\": null, \"uuid\": \"150888159a5947ada62853e1a319a406\", \"name\": \"dailyshow\", \"custom\": \"https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json\"}, \"image\": {\"thumb_120\": \"https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe\", \"uuid\": \"08bcd55b6fa34da1bfb94b18b9999b4e\"}, \"score\": 36}]}";
    @Test
    public void testParse() throws Exception {
        RecognitionResults results = parser.parse(JSON_RESULTS);
        assertEquals("dailyshow", getFirstResult(results).getCollectionName().get());
        assertEquals("https://crs-beta-catchoom.s3.amazonaws"
                        + ".com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json",
                getFirstResult(results).getCustomData().get());
        assertEquals("http://thedailyshow.cc.com/", getFirstResult(results).getTargetUrl().get());
        assertEquals(
                "https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe",
                getFirstResult(results).getTemplateImgThumbnailUrl().get());
    }

    private RecognitionResult getFirstResult(RecognitionResults results) {
        return results.getResults().get(0);
    }

    @Test
    public void testParseWhenEmptyResults() {
        RecognitionResults results = parser.parse(
                "{\n" + "            \"search_time\": 169,\n" + "                \"results\": []\n" + "        }");
        assertTrue(results.getResults().isEmpty());
    }

    @Test
    public void testParseWhenNullArg() {
        RecognitionResults res = parser.parse(null);
        assertTrue(res.getResults().isEmpty());
    }
}