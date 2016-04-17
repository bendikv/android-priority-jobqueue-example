package rid.team.jobqueue.jobqueue.news;

import rid.team.jobqueue.controllers.NewsController;
import rid.team.jobqueue.controllers.NewsEvents;
import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.data.vo.PayloadContent;
import rid.team.jobqueue.data.vo.Response;

/**
 * Created by bendik on 17.03.16.
 */
public class GetNewsContentJob extends BaseNewsJob {
    private final transient NewsController mNewsController;
    private final long id;

    public GetNewsContentJob(NewsController mNewsController, long id) {
        this.mNewsController = mNewsController;
        this.id = id;
    }

    @Override
    public void onRun() throws Throwable {
        PayloadContent payload = mNewsService.getNewsContent(id);
        if (Response.RESULT.OK.equals(payload.getResultCode())) {
            String content = payload.getPayload().getContent();
            mNewsModel.updateContent(id, content);
            News news = mNewsModel.load(id);
            mNewsController.handleNewsContent(news);
        } else {
            mNewsController.handleNetworkException(new NewsEvents.NewsListFetchFailed(payload));
        }
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        mNewsController.handleNetworkException(new NewsEvents.NetworkExceptionEvent(getNetworkException()));
    }
}
