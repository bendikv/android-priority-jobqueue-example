package rid.team.jobqueue.data.vo;

import com.google.gson.annotations.Expose;

/**
 * Created by bendik on 17.03.16.
 */
public class Response {
    public interface RESULT {
        String OK = "OK";
        String INVALID_REQUEST_DATA = "INVALID_REQUEST_DATA";
        String INTERNAL_ERROR = "INTERNAL_ERROR";
    }

    @Expose
    private String resultCode;
    @Expose
    private String errorMessage;
    @Expose
    private String plainMessage;
    @Expose
    private String trackingId;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPlainMessage() {
        return plainMessage;
    }

    public void setPlainMessage(String plainMessage) {
        this.plainMessage = plainMessage;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }
}
