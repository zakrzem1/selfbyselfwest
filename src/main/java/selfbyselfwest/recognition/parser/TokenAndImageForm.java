package selfbyselfwest.recognition.parser;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

public class TokenAndImageForm {
    @FormParam("token")
    //@PartType("text/plain")
    String token;

    @FormParam("image")
    @PartType("image/jpeg")
    byte[] image;

    public TokenAndImageForm(String token, byte[] image) {
        this.token = token;
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public Object getImage() {
        return image;
    }
}
