package co.id.jss.jssrestapi.common.payload;

public class CommonResponse {

    private String response;

    public CommonResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
