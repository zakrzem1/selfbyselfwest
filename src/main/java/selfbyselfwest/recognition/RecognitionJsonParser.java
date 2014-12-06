package selfbyselfwest.recognition;

import com.google.gson.Gson;

public class RecognitionJsonParser {
    RecognitionResults parse(String json){
        if(json==null){
            return RecognitionResults.EMPTY;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, RecognitionResults.class);
    }
}
