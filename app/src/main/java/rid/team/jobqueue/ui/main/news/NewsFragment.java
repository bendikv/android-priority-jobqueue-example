package rid.team.jobqueue.ui.main.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import rid.team.jobqueue.Constants;
import rid.team.jobqueue.R;
import rid.team.jobqueue.controllers.NewsController;
import rid.team.jobqueue.controllers.NewsEvents;
import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.ui.base.BaseListFragment;

/**
 * Created by bendik on 17.03.16.
 */
public class NewsFragment extends BaseListFragment {

    public static final String EXTRA_NEWS = "news";

    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.content) WebView content;

    private News news;

    @Inject
    NewsController mNewsController;

    public static Fragment newInstance(News news) {
        NewsFragment f = new NewsFragment();
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_NEWS, news);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news = (News) getArguments().getSerializable(EXTRA_NEWS);
        initView();
        refresh(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initView() {
        String title = news.getText();
        if (TextUtils.isEmpty(title)) {
            getActivity().setTitle(R.string.app_name);
        } else {
            getActivity().setTitle(title);
        }
        setSwipeContainer();
    }

    private void refresh(boolean force) {
        if (force || news.getContent() == null) {
            mNewsController.fetchNewsContent(news.getId());
            mNetworkProgressView.retry();
        } else {
            setContent();
            mNetworkProgressView.onSuccess();
        }
    }

    private void setSwipeContainer() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(true);
            }
        });
    }

    private void setContent() {
        content.loadDataWithBaseURL(Constants.WEB_BASE_URL, news.getContent(), "text/html", "utf-8", null);
    }

    @Subscribe
    public void onNewsFetchSuccessEventListener(NewsEvents.NewsFetchSuccess event) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNetworkProgressView.onSuccess();
        this.news = event.getNews();
        setContent();
    }

    @Subscribe
    public void onNewsFetchFailedEventListener(NewsEvents.NewsFetchFailed event) {
        mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(true);
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);

        if (!TextUtils.isEmpty(news.getContent())) {
            showSnakeBar(event.getResponse().getPlainMessage());
        } else {
            mNetworkProgressView.onError(event.getResponse().getPlainMessage());
        }
    }

    @Subscribe
    public void onNetworkExceptionEventListener(NewsEvents.NetworkExceptionEvent event) {
        mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(true);
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
        if (!TextUtils.isEmpty(news.getContent())) {
            showSnakeBar(getString(R.string.loading_failed));
        } else {
            mNetworkProgressView.onError(getString(R.string.server_error));
        }
    }

    private void showSnakeBar(String message) {
        Snackbar.make(content,
                message,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh(true);
                    }
                })
                .show();
    }

    @Override
    protected void onInject() {
        getFragmentComponent().inject(this);
    }
}
