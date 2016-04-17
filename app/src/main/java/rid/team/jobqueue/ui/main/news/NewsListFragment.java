package rid.team.jobqueue.ui.main.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rid.team.jobqueue.R;
import rid.team.jobqueue.controllers.NewsController;
import rid.team.jobqueue.controllers.NewsEvents;
import rid.team.jobqueue.data.vo.News;
import rid.team.jobqueue.ui.base.BaseListFragment;
import rid.team.jobqueue.ui.main.news.adapters.NewsRecyclerViewAdapter;
import rid.team.jobqueue.ui.widget.GridSpacingItemDecoration;

/**
 * Created by bendik on 17.03.16.
 */
public class NewsListFragment extends BaseListFragment {

    private static final String EXTRA_TITLE = "title";

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.mainLayout) CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;


    private List<News> mNewsList = new ArrayList<>();
    private NewsRecyclerViewAdapter mAdapter;
    private GridSpacingItemDecoration gridLayoutManager;

    @Inject
    NewsController mNewsController;

    public static Fragment newInstance() {
        return newInstance(null);
    }

    public static Fragment newInstance(String title) {
        NewsListFragment f = new NewsListFragment();
        Bundle b = new Bundle();
        b.putString(EXTRA_TITLE, title);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        String title = getArguments().getString(EXTRA_TITLE);
        if (TextUtils.isEmpty(title)) {
            getActivity().setTitle(R.string.app_name);
        } else {
            getActivity().setTitle(title);
        }

        setList();
        setSwipeContainer();
    }

    private void refresh(boolean force) {
        mNewsController.fetchNews(force);
        mNetworkProgressView.retry();
    }

    private void setSwipeContainer() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(true);
            }
        });
    }

    private void setGridList() {
        gridLayoutManager = new GridSpacingItemDecoration(getContext(), R.dimen.grid_list_spacing);

        mAdapter = new NewsRecyclerViewAdapter(mNewsList, getActivity());
        mAdapter.setCallback(new NewsRecyclerViewAdapter.Callback() {
            @Override
            public void onNewsClick(News news) {
                startActivity(intentFor(getActivity(), news));
            }
        });
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(gridLayoutManager);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setList() {
        mAdapter = new NewsRecyclerViewAdapter(mNewsList, getActivity());
        mAdapter.setCallback(new NewsRecyclerViewAdapter.Callback() {
            @Override
            public void onNewsClick(News news) {
                startActivity(intentFor(getActivity(), news));
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.removeItemDecoration(gridLayoutManager);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static Intent intentFor(Context context, News news) {
        Intent intent = new Intent(context, DetailedNewsActivity.class);
        intent.putExtra(NewsFragment.EXTRA_NEWS, news);
        return intent;
    }

    @Subscribe
    public void onNewsListFetchSuccessEventListener(NewsEvents.NewsListFetchSuccess event) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNetworkProgressView.onSuccess();
        mNewsList.clear();
        mNewsList.addAll(event.getNewsList());
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onNewsListFetchFailedEventListener(NewsEvents.NewsListFetchFailed event) {
        mNetworkProgressView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(true);
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);

        if (mNewsList.size()>0) {
            showSnakeBar(R.string.loading_failed, event.getResponse().getPlainMessage());
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
        if (mNewsList.size()>0) {
            showSnakeBar(R.string.loading_failed, getString(R.string.server_error));
        } else {
            mNetworkProgressView.onError(getString(R.string.server_error));
        }
    }

    private void showSnakeBar(int title, String message) {
        Snackbar.make(mRecyclerView,
                getString(title, message),
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
