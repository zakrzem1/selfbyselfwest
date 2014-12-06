package selfbyselfwest.recognition;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public interface RecognitionClient {
    @POST
    @Path("/v1/search")
    @Produces("application/json")
    @Consumes("multipart/form-data")
    RecognitionResult analyzeImg(@MultipartForm TokenAndImageForm tokenAndImageForm);
}
