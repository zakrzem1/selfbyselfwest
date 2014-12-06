package selfbyselfwest.recognition;

/**
 *
 */
public class StatusAndBody {
    int status;
    String body;

    public StatusAndBody(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
