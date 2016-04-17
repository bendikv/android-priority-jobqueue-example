package rid.team.jobqueue.data.vo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by bendik on 17.03.16.
 */
public class PublicationDate implements Serializable{
    @Expose
    private long milliseconds;

    public PublicationDate(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getSeconds() {
        return milliseconds / 1000;
    }
}
