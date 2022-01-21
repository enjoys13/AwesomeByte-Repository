package co.id.jss.jssrestapi.common.exception;

import org.springframework.http.HttpStatus;

public class VarException extends Exception {

    private HttpStatus httpStatus;
    private int httpStatusCode;
    private String httpStatusName;
    private String uri;

    public VarException(HttpStatus httpStat, String msg) {
        super(msg);
        this.httpStatus = httpStat;
        this.httpStatusCode = httpStat.value();
        this.httpStatusName = httpStat.name();
//        this.uri = Util.isNull(Util.getCurrentUserDetail().getCurrentURI()) ? "/" : "";
    }

    public VarException(HttpStatus httpStat, String uri, String msg) {
        super(msg);
        this.httpStatus = httpStat;
        this.httpStatusCode = httpStat.value();
        this.httpStatusName = httpStat.name();
        this.uri = uri;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpStatusName() {
        return httpStatusName;
    }

    public void setHttpStatusName(String httpStatusName) {
        this.httpStatusName = httpStatusName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
