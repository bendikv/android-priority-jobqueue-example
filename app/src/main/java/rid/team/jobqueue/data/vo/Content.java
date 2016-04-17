package rid.team.jobqueue.data.vo;

import com.google.gson.annotations.Expose;

/**
 * Created by bendik on 18.03.16.
 */
public class Content {
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
