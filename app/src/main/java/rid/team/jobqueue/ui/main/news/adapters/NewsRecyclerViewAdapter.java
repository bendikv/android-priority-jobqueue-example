package rid.team.jobqueue.ui.main.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rid.team.jobqueue.R;
import rid.team.jobqueue.data.vo.News;

/**
 * Created by bendik on 17.03.16.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    private final List<News> mNewsList;
    private final Context mContext;
    private NewsViewHolder vh;
    private Callback callback = null;

    public NewsRecyclerViewAdapter(List<News> news, Context context) {
        mNewsList = news;
        mContext = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_news_grid, parent, false);
        vh = new NewsViewHolder(view);
        vh.setCallback(callback);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder h, int position) {
        News news = mNewsList.get(position);
        h.bind(mContext, news);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.row) ViewGroup mViewGroupMain;
        @Bind(R.id.text_view_title) TextView mTextViewPrimary;
        @Bind(R.id.text_view_date) TextView mTextViewSecondary;

        private Context mContext;
        private News mNews;
        private final SimpleDateFormat formatter;
        private Callback callback = null;

        public NewsViewHolder(View itemView) {
            super(itemView);
            formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            ButterKnife.bind(this, itemView);
        }

        public void bind(Context context, News item) {
            this.mContext = context;
            this.mNews = item;

            mTextViewPrimary.setText(Html.fromHtml(item.getText()).toString());
            Date date = new Date(item.getPublicationDate().getMilliseconds());
            mTextViewSecondary.setText(formatter.format(date));
        }

        @OnClick(R.id.row)
        public void onNewsItemClicked() {
            if (callback != null) {
                callback.onNewsClick(this.mNews);
            }
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
        if (vh != null) {
            this.vh.setCallback(callback);
        }
    }

    public interface Callback {
        void onNewsClick(News news);
    }

}
