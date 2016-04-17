package rid.team.jobqueue.controllers;

import java.util.List;

import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.data.vo.Response;
import rid.team.jobqueue.jobqueue.NetworkException;

/**
 * Created by bendik on 17.03.16.
 */
public class NewsEvents {
    public static class NewsListFetchSuccess {
        private final List<News> newsList;

        public NewsListFetchSuccess(List<News> newsList){
            this.newsList = newsList;
        }

        public List<News> getNewsList() {
            return newsList;
        }
    }

    public static class NewsListFetchFailed {
        private final Response response;

        public NewsListFetchFailed(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }
    }

    public static class NewsFetchSuccess {
        private final News news;

        public NewsFetchSuccess(News news){
            this.news = news;
        }

        public News getNews() {
            return news;
        }
    }

    public static class NewsFetchFailed {
        private final Response response;

        public NewsFetchFailed(Response response) {
            this.response = response;
        }

        public Response getResponse() {
            return response;
        }
    }

    public static class NetworkExceptionEvent {
        private final NetworkException exeption;

        public NetworkExceptionEvent(NetworkException e) {
            this.exeption = e;
        }

        public NetworkException getExeption() {
            return exeption;
        }
    }

}
