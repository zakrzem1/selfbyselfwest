package selfbyselfwest.recognition.parser;

import com.google.gson.Gson;

public class RecognitionJsonParser {
    public RecognitionResults parse(String json){
        if(json==null){
            return RecognitionResults.EMPTY;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, RecognitionResults.class);
    }

    //FIXME scan failure case
    /**
     * {
     "error": {
     "code": "ERROR_CODE",
     "message": "A verbose description of the error"
     }
     }
     */
}
