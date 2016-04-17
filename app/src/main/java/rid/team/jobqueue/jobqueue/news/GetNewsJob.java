package rid.team.jobqueue.jobqueue.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rid.team.jobqueue.controllers.NewsController;
import rid.team.jobqueue.controllers.NewsEvents;
import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.data.vo.PayloadNewsList;
import rid.team.jobqueue.data.vo.Response;

/**
 * Created by bendik on 17.03.16.
 */
public class GetNewsJob extends BaseNewsJob {
    private final transient NewsController mNewsController;
    private final boolean force;

    public GetNewsJob(NewsController mNewsController, boolean force) {
        this.mNewsController = mNewsController;
        this.force = force;
    }

    @Override
    public void onRun() throws Throwable {
        // Достаём новости из БД, если они там есть
        List<News> oldNews = mNewsModel.loadAll();
        Collections.sort(oldNews, new NewsComparator());
        if (oldNews != null && oldNews.size()>0) {
            mNewsController.handleNewsList(oldNews);
        }

        // Делаем запрос к сети
        if (force || oldNews == null || (oldNews != null && oldNews.size() == 0)) {
            PayloadNewsList payload = mNewsService.getNews();
            if (Response.RESULT.OK.equals(payload.getResultCode())) {
                List<News> news = payload.getPayload();
                Collections.sort(news, new NewsComparator());
                if (!areListsTheSame(oldNews, news)) {
                    mNewsModel.saveAll(news);
                    mNewsController.handleNewsList(news);
                }
            } else {
                mNewsController.handleNetworkException(new NewsEvents.NewsListFetchFailed(payload));
            }
        }
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        mNewsController.handleNetworkException(new NewsEvents.NetworkExceptionEvent(getNetworkException()));
    }

    private boolean areListsTheSame(List<News> oldNews, List<News> news) {
        List a = new ArrayList(oldNews);
        List b = new ArrayList(news);
        List c = new ArrayList(b);
        c.retainAll(a);
        b.removeAll(a);
        return b.size() == 0;
    }

    private class NewsComparator implements java.util.Comparator<News> {
        @Override
        public int compare(News a, News b) {
            return (int) (b.getPublicationDate().getSeconds() - a.getPublicationDate().getSeconds());
        }
    }
}
