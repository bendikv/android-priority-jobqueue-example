package rid.team.jobqueue.data.vo;

import com.google.gson.annotations.Expose;

/**
 * Created by bendik on 17.03.16.
 */
public class PayloadContent extends Response {
    @Expose
    private Content payload;

    public Content getPayload() {
        return payload;
    }

    public void setPayload(Content payload) {
        this.payload = payload;
    }
}
