package rid.team.jobqueue.controllers;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.jobqueue.news.GetNewsContentJob;
import rid.team.jobqueue.jobqueue.news.GetNewsJob;

/**
 * Created by bendik on 17.03.16.
 */
public class NewsController {
    private final Bus mBus;
    private final JobManager mJobManager;

    @Inject
    public NewsController(Bus mBus, JobManager jobManager) {
        this.mBus = mBus;
        this.mJobManager = jobManager;
    }

    public void fetchNews(){
        fetchNews(false);
    }

    public void fetchNews(boolean force){
        mJobManager.addJob(new GetNewsJob(this, force));
    }


    public void fetchNewsContent(long id) {
        mJobManager.addJob(new GetNewsContentJob(this, id));
    }

    /*
     * Обработка данных
     */

    public void handleNetworkException(NewsEvents.NetworkExceptionEvent networkExceptionEvent) {
        mBus.post(networkExceptionEvent);
    }

    public void handleNetworkException(NewsEvents.NewsListFetchFailed newsListFetchFailed) {
        mBus.post(newsListFetchFailed);
    }

    public void handleNewsList(List<News> newsList) {
        mBus.post(new NewsEvents.NewsListFetchSuccess(newsList));
    }

    public void handleNetworkException(NewsEvents.NewsFetchFailed newsFetchFailed) {
        mBus.post(newsFetchFailed);
    }

    public void handleNewsContent(News news) {
        mBus.post(new NewsEvents.NewsFetchSuccess(news));
    }
}
