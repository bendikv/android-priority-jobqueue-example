package rid.team.jobqueue.api.service;

import retrofit.http.GET;
import retrofit.http.Query;
import rid.team.jobqueue.data.vo.PayloadContent;
import rid.team.jobqueue.data.vo.PayloadNewsList;

/**
 * Created by bendik on 17.03.16.
 */
public interface NewsService {
    @GET("/news")
    PayloadNewsList getNews();
    @GET("/news_content")
    PayloadContent getNewsContent(@Query("id") long id);
}
