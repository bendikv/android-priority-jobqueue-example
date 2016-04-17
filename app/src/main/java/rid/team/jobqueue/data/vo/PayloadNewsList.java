package rid.team.jobqueue.data.vo;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by bendik on 17.03.16.
 */
public class PayloadNewsList extends Response {

    @Expose
    private List<News> payload;

    public List<News> getPayload() {
        return payload;
    }

    public void setPayload(List<News> payload) {
        this.payload = payload;
    }
}
