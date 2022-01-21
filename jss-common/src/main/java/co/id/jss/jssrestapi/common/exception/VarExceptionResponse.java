package co.id.jss.jssrestapi.common.exception;

import javax.servlet.http.HttpServletRequest;

public class VarExceptionResponse {

    private long timestamp;
    private String status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public VarExceptionResponse(VarException ex) {
        this.timestamp = System.currentTimeMillis();
        this.status = Integer.toString(ex.getHttpStatus().value());
        this.error = ex.getHttpStatus().name();
        this.exception = ex.getClass().getCanonicalName();
        this.message = ex.getMessage();
        this.path = "/";

    }

    public VarExceptionResponse(VarException ex, HttpServletRequest request) {
        this.timestamp = System.currentTimeMillis();
        this.status = Integer.toString(ex.getHttpStatus().value());
        this.error = ex.getHttpStatus().name();
        this.exception = ex.getClass().getCanonicalName();
        this.message = ex.getMessage();
        this.path = request.getRequestURI();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}

